package com.decagon.safariwebstore.service.serviceImplementation;

import com.decagon.safariwebstore.model.*;
import com.decagon.safariwebstore.payload.request.ProductRequest;
import com.decagon.safariwebstore.repository.FavouriteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavouriteServiceImplementationTest {
    @Mock
    FavouriteRepository favouriteRepository;
    @InjectMocks
    FavouriteServiceImplementation favouriteServiceImplementation;

    @Test
    void customerAddProductToFavorite() {
        Favourite favourite = new Favourite();
        favourite.setId(2L);
        favourite.setUserId(3L);
        favourite.setProductId(1L);

        final Long productId = 1L;
        final Long userId = 3L;

        Mockito.lenient().when(favouriteRepository.findFavouriteByUserIdAndProductId(userId,productId)).thenReturn(java.util.Optional.of(favourite));
        Mockito.lenient().when(favouriteRepository.save(favourite)).thenReturn(favourite);

    }

    @Test
    void getAllFavouriteProductsTest(){
        Long userId = 1L;

        List<Favourite> favourites = new ArrayList<>();
        favourites.add(new Favourite(userId, 2L));
        favourites.add(new Favourite(userId, 3L));
        favourites.add(new Favourite(userId, 4L));

        Mockito.lenient().when(favouriteRepository.findAllByUserId(userId)).thenReturn(favourites);
    }

    @Test
    void getSingleFavouriteProductsByIdTest(){
        Long userId = 1L;
        Long productId = 2L;

        Favourite favourite = new Favourite(userId, productId);

        Mockito.lenient().when(favouriteRepository.findFavouriteByUserIdAndProductId(userId, productId)).thenReturn(java.util.Optional.of(favourite));
    }

    @Test
    void willNotGetSingleFavouriteProductsByIdTest(){
        Long userId = 1L;
        Long productId = 2L;

        Favourite favourite = new Favourite(userId, productId);

        given(favouriteRepository.findFavouriteByUserIdAndProductId(userId, productId)).willReturn(Optional.of(favourite));

        assertThrows(NullPointerException.class,() -> {
            favouriteServiceImplementation.getFavouriteProductsById(favourite.getProductId());
        });

        verify(favouriteRepository, never()).findFavouriteByUserIdAndProductId(userId, productId);

        Mockito.lenient().when(favouriteRepository.findFavouriteByUserIdAndProductId(userId, productId)).thenReturn(java.util.Optional.of(favourite));
    }

}