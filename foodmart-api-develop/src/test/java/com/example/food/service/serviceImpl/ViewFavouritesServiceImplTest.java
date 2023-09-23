package com.example.food.service.serviceImpl;


import com.example.food.model.Category;
import com.example.food.model.Favourites;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.FavouriteProductResponse;
import com.example.food.repositories.FavouritesRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.services.serviceImpl.FavouritesServiceImpl;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViewFavouritesServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private FavouritesRepository favouritesRepository;
    @Mock
    private UserUtil userUtil;
    @Mock
    ResponseCodeUtil responseCodeUtil;

    @InjectMocks
    private FavouritesServiceImpl favouritesService;
    FavouriteProductResponse response;
    Product product = null;
    Category category = new Category();
    Users users = null;

    @BeforeEach
    public void setUp() {
        category.setId(1L);
        category.setCategoryName("Sweet");

        product = Product.builder()
                .id(254L)
                .productName("Beverage")
                .productPrice(new BigDecimal(2500))
                .imageUrl("www.cloudinary.image.html")
                .description("mensa")
                .quantity(20)
                .category(category)
                .build();

        users = Users.builder()
                .id(1L)
                .email("email@example.com")
                .build();
    }

    @Test
    public void testViewAFavouriteProduct_withFavouriteIdIsNull() {
        FavouriteProductResponse favouriteProductResponse = favouritesService.viewAFavouriteProduct(null);
        assertEquals("favourite ID is null", favouriteProductResponse.getDescription());
    }

    @Test
    public void testViewAFavouriteProduct_withUserNotFound() {
        FavouriteProductResponse favouriteProductResponse = favouritesService.viewAFavouriteProduct(232L);
        assertEquals("Email does not exist", favouriteProductResponse.getDescription());
    }

    @Test
    void viewAFavouriteProduct() {
        Favourites favourites = new Favourites(232L, 1L, 254L);

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("email@example.com");
        when(userRepository.findByEmail("email@example.com")).thenReturn(Optional.of(users));
        when(favouritesRepository.findById(anyLong())).thenReturn(Optional.of(favourites));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        FavouriteProductResponse favouriteProductResponse = favouritesService.viewAFavouriteProduct(232L);

        verify(favouritesRepository, times(1)).findById(232L);
        assertNotNull(favouriteProductResponse);
    }
}
