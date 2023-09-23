package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.ProductDto;
import com.example.food.exceptions.ResourceNotFoundException;
import com.example.food.exceptions.UserNotFoundException;
import com.example.food.model.Favourites;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.FavouriteProductResponse;
import com.example.food.repositories.FavouritesRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.FavouritesService;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FavouritesServiceImpl implements FavouritesService {
    private final UserRepository userRepository;

    private final ProductRepository productRepository;
    private final FavouritesRepository favouritesRepository;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();

    private final UserUtil userUtil;

    @Override
    public BaseResponse addToFavourites(Long productId) {
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(loggedInUser.toString());

        Users user = userRepository.findByEmail(loggedInUser.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User does not exist. Please check and try again."));

        Product favouriteProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not exist"));

        Boolean alreadyInFavourite = favouritesRepository.existsByUsersIdAndProductId(user.getId(), favouriteProduct.getId());

        BaseResponse response = new BaseResponse();

        if (!alreadyInFavourite) {
            try {
                Favourites favourites = new Favourites();
                favourites.setUsersId(user.getId());
                favourites.setProductId(favouriteProduct.getId());
                favouritesRepository.save(favourites);
                return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS, "added to favourite");
            } catch (Exception e) {
                log.error("Either User or Product not registered: {}", e.getMessage());
            }
        }
        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR, favouriteProduct.getProductName() + " is already your favourite");
    }

    @Override
    public FavouriteProductResponse viewAFavouriteProduct(Long favouriteId) {
        FavouriteProductResponse response = new FavouriteProductResponse();

        if (favouriteId == null) {
            Map<String, String> params = new HashMap<>(1);
            params.put("errorMessage", "favourite ID is null");
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR, params.get("errorMessage"));
        }

        String email = userUtil.getAuthenticatedUserEmail();
        Optional<Users> users = userRepository.findByEmail(email);

        if (users.isEmpty()) {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.USER_NOT_FOUND);
        }

        Optional<Favourites> optionalFavourites = favouritesRepository.findById(favouriteId);
        if (optionalFavourites.isEmpty()) {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR, "No favourite products found");
        }

        Product product = productRepository.findById(optionalFavourites.get().getProductId()).get();

        response.setFavouriteProduct(productToProductDto(product));

        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS);
    }

    static ProductDto productToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setProductName(product.getProductName());
        productDto.setQuantity(product.getQuantity());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setCategoryName(product.getCategory().getCategoryName());
        return productDto;
    }

    public BaseResponse removeFromFavourites(Long productId) {
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(loggedInUser.toString());

        Users user = userRepository.findByEmail(loggedInUser.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User does not exist. Please check and try again."));

        BaseResponse response = new BaseResponse();

        Optional<Favourites> favouriteProduct = favouritesRepository.findByUsersIdAndProductId(user.getId(), productId);

        if (favouriteProduct.isPresent()) {
            favouritesRepository.delete(favouriteProduct.get());
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS, "Product has been removed from favourites!");
        }
        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR, "User does not exist or Product not favourite");
    }
}

