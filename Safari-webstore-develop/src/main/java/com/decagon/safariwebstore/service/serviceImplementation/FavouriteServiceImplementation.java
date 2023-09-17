package com.decagon.safariwebstore.service.serviceImplementation;

import com.decagon.safariwebstore.exceptions.BadRequestException;
import com.decagon.safariwebstore.exceptions.ResourceNotFoundException;
import com.decagon.safariwebstore.model.Favourite;
import com.decagon.safariwebstore.model.Product;
import com.decagon.safariwebstore.model.ProductPage;
import com.decagon.safariwebstore.model.User;
import com.decagon.safariwebstore.repository.FavouriteRepository;
import com.decagon.safariwebstore.repository.ProductRepository;
import com.decagon.safariwebstore.security.service.UserDetailsImpl;
import com.decagon.safariwebstore.service.FavouriteService;
import com.decagon.safariwebstore.utils.MethodUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FavouriteServiceImplementation implements FavouriteService {
    @Autowired
    FavouriteRepository favouriteRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserServiceImplementation userServiceImplementation;
    @Override
    public String customerAddProductToFavorite(UserDetailsImpl userImpl, Long productId) {
        User user = userServiceImplementation.findUserByEmail(userImpl.getUsername());
        Long userId = user.getId();
        Optional<Favourite> favouriteOptional = favouriteRepository.findFavouriteByUserIdAndProductId(userId,productId);
        if(favouriteOptional.isPresent()){
            favouriteRepository.deleteById(favouriteOptional.get().getId());
            return "Product removed from " + user.getFirstName() + " Favorite successfully";
        }else{
            Favourite newFavorite = new Favourite();
            newFavorite.setProductId(productId);
            newFavorite.setUserId(userId);
            favouriteRepository.save(newFavorite);
            return "Product successfully added to " + user.getFirstName() + " Favorite";
        }

    }

    @Override
    public ResponseEntity<?> getFavouriteProducts(UserDetailsImpl userImpl) {
        User user = userServiceImplementation.findUserByEmail(userImpl.getUsername());
        log.info("user {}", user);
        List<Favourite> favourites = favouriteRepository.findAllByUserId(user.getId());
        if(favourites.size()==0)throw new BadRequestException("No favourite Product");
        List<Product> products = favourites.stream().map(product -> {
            return productRepository.findProductById(product.getProductId());
        }).collect(Collectors.toList());
        Page<Product> page = new PageImpl<>(products);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getFavouriteProductsById(Long productId) {
        UserDetailsImpl userImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userServiceImplementation.findUserByEmail(userImpl.getUsername());
        Optional<Favourite> favourites = favouriteRepository.findFavouriteByUserIdAndProductId(user.getId(), productId);
        if(favourites.isEmpty()) throw new ResourceNotFoundException("Incorrect parameter; productId " + productId + " does not exist");
        Product product = productRepository.findProductById(favourites.get().getProductId());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
