package com.example.food.service.serviceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.model.Favourites;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.repositories.FavouritesRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.serviceImpl.FavouritesServiceImpl;
import com.example.food.util.ResponseCodeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class FavouritesServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    FavouritesRepository favouritesRepository;
    @Mock
    ResponseCodeUtil responseCodeUtil;
    @InjectMocks
    FavouritesServiceImpl favouritesServiceImpl;
    Product mockedProduct;
    @BeforeEach
    public void setup() {
        // set up the SecurityContext to return a mocked userDetails object
        UserDetails mockedUserDetails = mock(UserDetails.class);
        when(mockedUserDetails.getUsername()).thenReturn("faith@abiola.com");
        SecurityContextHolder.getContext().setAuthentication(new org.springframework.security.authentication
                .UsernamePasswordAuthenticationToken(mockedUserDetails, "password", new ArrayList<>()));
        Users mockedUser = new Users();
        mockedUser.setId(1L);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(mockedUser));
        mockedProduct = new Product();
        mockedProduct.setId(1L);
        mockedProduct.setProductName("Apple");
        when(productRepository.findById(any())).thenReturn(Optional.of(mockedProduct));
    }
    @Test
    public void testAddToFavourites() {
        when(favouritesRepository.existsByUsersIdAndProductId(any(), any())).thenReturn(false);
        BaseResponse baseResponse = favouritesServiceImpl.addToFavourites(1L);
        assertEquals(baseResponse.getDescription(), "added to favourite");
    }
    @Test
    public void testAddToFavouritesIfAlreadyExisting() {
        when(favouritesRepository.existsByUsersIdAndProductId(any(), any())).thenReturn(true);
        BaseResponse actualResponse = favouritesServiceImpl.addToFavourites(1L);
        assertEquals(actualResponse.getDescription(), mockedProduct.getProductName() + " is already your favourite");
    }

}

