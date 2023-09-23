//package com.decagon.fitnessoapp.service.serviceImplementation;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.decagon.fitnessoapp.model.product.CHANGE_QUANTITY;
//import com.decagon.fitnessoapp.model.product.IntangibleProduct;
//import com.decagon.fitnessoapp.model.product.TangibleProduct;
//import com.decagon.fitnessoapp.repository.IntangibleProductRepository;
//import com.decagon.fitnessoapp.repository.PersonRepository;
//import com.decagon.fitnessoapp.repository.TangibleProductRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ContextConfiguration(classes = {ShoppingCartServiceImpl.class, PersonDetails.class})
//@ExtendWith(SpringExtension.class)
//class ShoppingCartServiceImplTest {
//    @MockBean
//    private IntangibleProductRepository intangibleProductRepository;
//
//    @MockBean
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private PersonDetails personDetails;
//
//    @MockBean
//    private PersonRepository personRepository;
//
//    @MockBean
//    private ShoppingCartRepository shoppingCartRepository;
//
//    @Autowired
//    private ShoppingCartServiceImpl shoppingCartServiceImpl;
//
//    @MockBean
//    private TangibleProductRepository tangibleProductRepository;
//
//    @Test
//    void testAddToCart() throws IllegalArgumentException {
//        TangibleProduct tangibleProduct = new TangibleProduct();
//        tangibleProduct.setCategory("Category");
//        tangibleProduct.setDescription("The characteristics of someone or something");
//        tangibleProduct.setId(123L);
//        tangibleProduct.setImage("Image");
//        tangibleProduct.setPrice(BigDecimal.valueOf(42L));
//        tangibleProduct.setProductName("Product Name");
//        tangibleProduct.setQuantity(1);
//        tangibleProduct.setStock(1L);
//        tangibleProduct.setStockKeepingUnit("Stock Keeping Unit");
//        Optional<TangibleProduct> ofResult = Optional.of(tangibleProduct);
//        when(this.tangibleProductRepository.findById((Long) any())).thenReturn(ofResult);
//        when(this.objectMapper.convertValue((Object) any(), (Class<Object>) any()))
//                .thenThrow(new IllegalStateException("foo"));
//
//        IntangibleProduct intangibleProduct = new IntangibleProduct();
//        intangibleProduct.setCategory("Category");
//        intangibleProduct.setDescription("The characteristics of someone or something");
//        intangibleProduct.setMonthlySubscription(1);
//        intangibleProduct.setId(123L);
//        intangibleProduct.setImage("Image");
//        intangibleProduct.setPrice(BigDecimal.valueOf(42L));
//        intangibleProduct.setProductName("Product Name");
//        intangibleProduct.setStock(1L);
//        intangibleProduct.setStockKeepingUnit("Stock Keeping Unit");
//        Optional<IntangibleProduct> ofResult1 = Optional.of(intangibleProduct);
//        when(this.intangibleProductRepository.findById((Long) any())).thenReturn(ofResult1);
//        assertThrows(IllegalStateException.class,
//                () -> this.shoppingCartServiceImpl.addToCart(123L, CHANGE_QUANTITY.INCREASE, this.personDetails));
//        verify(this.tangibleProductRepository).findById((Long) any());
//        verify(this.objectMapper).convertValue((Object) any(), (Class<Object>) any());
//        verify(this.intangibleProductRepository).findById((Long) any());
//    }
//}
//
