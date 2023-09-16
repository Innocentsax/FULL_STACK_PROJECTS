package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.exceptions.ResourceNotFoundException;
import com.decagon.chompapp.exceptions.UserNotFoundException;
import com.decagon.chompapp.models.Favorites;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.FavoriteRepository;
import com.decagon.chompapp.repositories.ProductRepository;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.services.FavoritesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class FavoritesServiceImpl implements FavoritesService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FavoriteRepository favoriteRepository;


    @Override
    public ResponseEntity<String> addProductToFavorite(Long productId) {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(loggedInUser.toString());

        User user = userRepository.findByEmail(loggedInUser.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User with this id does not exist. " +
                        "Please check and try again."));
        Product favoriteProduct = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product with this Id does not exit!"));

        Boolean favorites = favoriteRepository.existsByUserAndProduct(user, favoriteProduct);

        if (!favorites){
            Favorites fav = new Favorites();
            fav.setUser(user);
            fav.setProduct(favoriteProduct);
            favoriteRepository.save(fav);
            return new ResponseEntity<>(favoriteProduct.getProductName() + " added to favorite", HttpStatus.OK);
        }

        return new ResponseEntity<>(favoriteProduct.getProductName() + " is already your favorite", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> removeProductFromFavorite(Long productId) {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(loggedInUser.toString());

        User user = userRepository.findByEmail(loggedInUser.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User with this id does not exist. " +
                        "Please check and try again."));

        Favorites favoriteProduct = favoriteRepository.findFavoritesByProduct_ProductIdAndUser_UserId (productId, user.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("This product dose not exist in your favorite"));

        favoriteRepository.delete(favoriteProduct);
        return new ResponseEntity<>(favoriteProduct.getProduct().getProductName() + " has been removed.", HttpStatus.OK);
    }
}
