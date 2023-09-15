package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.ViewProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Favorite;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.exception.NotFoundException;
import com.example.cedarxpressliveprojectjava010.exception.ProductNotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.FavoriteRepository;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final ModelMapper mapper;

    @Override
    public ResponseEntity<String> addProductToFavorite(Long productId) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByEmail(loggedInEmail);

        Product product1 = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("product with ID " + productId + " Does not Exist"));

        Optional<Favorite> favorite = favoriteRepository.findById(product1.getId());
        if (favorite.isPresent()) {
            throw new ProductNotFoundException("product with ID " + productId +
                    " already exist in your favorite");
        } else {
            Favorite favorite1 = new Favorite();
            favorite1.setUser(user);
            favorite1.setProduct(product1);
            favoriteRepository.save(favorite1);
            return new ResponseEntity<>(product1.getProductName() + " added to favorite", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> deleteProductFromFavorite(Long productId, Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with this" + " " + userId + " does not exist "));

        Favorite addedFavoriteProduct = favoriteRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Resources does not exist"));
        favoriteRepository.delete(addedFavoriteProduct);

        return new ResponseEntity<>("Product successfully deleted", HttpStatus.OK);
    }

    @Override
    public List<ViewProductDto> fetchAllFavoriteProduct(int pageNo, int pageSize) {

        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByEmail(loggedInEmail);

        List<Favorite> favorite = favoriteRepository.findByUser(user);
        if(favorite != null){

            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Favorite> favoriteProducts = favoriteRepository.findFavoritesByUser(user, pageable);
            List<Favorite> productList = favoriteProducts.getContent();

            return productList.stream().map(a -> ViewProductDto.builder()
                    .productName(a.getProduct().getProductName())
                    .description(a.getProduct().getDescription())
                    .price(a.getProduct().getPrice())
                    .build()).collect(Collectors.toList());
        }else{
            return null;
        }
    }
    @Override
    public ResponseEntity<ViewProductDto> fetchSingleFavoriteProduct(Long productId){
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByEmail(loggedInEmail);

        List<Favorite> favorite = favoriteRepository.findByUser(user);
        if(favorite != null){
            Optional<Product> product = Optional.ofNullable(productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId + " not available")));
            Favorite favoriteProduct = favoriteRepository.findFavoritesByUserAndProduct(user, product.get());
            if(favoriteProduct != null){
                return new ResponseEntity<>(mapToDto(favoriteProduct), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    private ViewProductDto mapToDto(Favorite favorite){


        return ViewProductDto.builder()
                .productName(favorite.getProduct().getProductName())
                .description(favorite.getProduct().getDescription())
                .price(favorite.getProduct().getPrice())
                .build();
    }
}
