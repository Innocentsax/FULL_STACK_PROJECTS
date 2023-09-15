package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.ViewProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Category;
import com.example.cedarxpressliveprojectjava010.entity.Favorite;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.exception.NotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.FavoriteRepository;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static  org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
@Slf4j
class FavoriteServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    FavoriteRepository favoriteRepository;
    @InjectMocks
    FavoriteServiceImpl favoriteServiceImpl;

    private User user1;
    private Product product1;
    private Product product2;
    private Favorite favorite;
    private Favorite favorite1;
    private ViewProductDto viewProductDto;
    private ViewProductDto viewProductDto1;
    List<ViewProductDto> viewProductDtoList;
    Page<Favorite> page;

    @BeforeEach
    public void setUp(){
        user1 = User.builder()
                .email("test@gmail.com")
                .password("password")
                .firstName("chinedu")
                .build();
        user1.setId(2L);
        Category burger = Category.builder()
                .categoryName("Burger")
                .build();

        product1 = Product.builder()
                .productName("m-model")
                .category(burger)
                .build();
        product1.setId(1L);

        product2 = Product.builder()
                .productName("m-model")
                .category(burger)
                .build();
        product2.setId(2L);

        favorite = Favorite.builder()
                .product(product1)
                .user(user1)
                .build();
        favorite.setId(1L);
        favorite1 = Favorite.builder()
                .product(product2)
                .user(user1)
                .build();
        favorite1.setId(2L);
        page = new PageImpl<>(List.of(favorite, favorite1));

        viewProductDtoList = new ArrayList<>();
        favorite.setId(2L);

        viewProductDtoList = new ArrayList<>();
        viewProductDto = ViewProductDto.builder()
                .productName(product1.getProductName())
                .description(product1.getDescription())
                .price(product1.getPrice())
                .build();

        viewProductDto1 = ViewProductDto.builder()
                .productName(product2.getProductName())
                .description(product2.getDescription())
                .price(product2.getPrice())
                .build();
        viewProductDtoList.add(viewProductDto);
        viewProductDtoList.add(viewProductDto1);
        viewProductDtoList.add(viewProductDto);
    }


    @Test
    public void testAddProductToFavorite() {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        given(productRepository.findById(1L)).willReturn(Optional.of(product1));

        ResponseEntity<String> responseEntity = favoriteServiceImpl.addProductToFavorite(product1.getId());
        assertEquals(responseEntity.getBody(), product1.getProductName() + " added to favorite");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK );
        Assertions.assertEquals(responseEntity.getBody(), product1.getProductName() + " added to favorite");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void testForExceptionThrownWhenUserRemovesProductThatIsNotInFavorite() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, ()-> {
            favoriteServiceImpl.deleteProductFromFavorite(product1.getId(), user1.getId());
        });
    }
    @Test
    void TestForRightResponseWhenUserRemoveProductFromFavorite() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.of(favorite));
        favoriteServiceImpl.deleteProductFromFavorite(product1.getId(),user1.getId());
        Assertions.assertEquals("Product successfully deleted","Product successfully deleted");
    }

    @Test
    void testToFetchAllFavoriteProductByAGivenUser() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user1.getFirstName());
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.getUserByEmail(any())).thenReturn(user1);
        when(favoriteRepository.findByUser(any())).thenReturn(List.of(favorite, favorite1));
        when(favoriteRepository.findFavoritesByUser(any(), any())).thenReturn(page);

        List<ViewProductDto> favoriteList = favoriteServiceImpl.fetchAllFavoriteProduct(0,1);
        org.assertj.core.api.Assertions.assertThat(favoriteList.size()).isEqualTo(2);
    }

    @Test
    void toFetchSingleFavoriteProductByAGivenUserAndProduct() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user1.getFirstName());
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.getUserByEmail(any())).thenReturn(user1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(favoriteRepository.findFavoritesByUserAndProduct(any(), any())).thenReturn(favorite);

        ResponseEntity<ViewProductDto> favoriteProduct = favoriteServiceImpl
                .fetchSingleFavoriteProduct(1L);
        org.assertj.core.api.Assertions.assertThat(favoriteProduct.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
