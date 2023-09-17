package com.decagon.safariwebstore.service.serviceImplementation;

import com.decagon.safariwebstore.model.CartItem;
import com.decagon.safariwebstore.model.Product;
import com.decagon.safariwebstore.model.ProductPage;
import com.decagon.safariwebstore.model.User;
import com.decagon.safariwebstore.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceImplementationTest {
    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private  UserRepository userRepository;

    @InjectMocks
    private CartItemServiceImplementation cartItemServiceImplementation;

    @Mock private CategoryRepository categoryRepository;

    @Mock private SubCategoryRepository subCategoryRepository;

    private SizeRepository sizeRepository;
    private ColorRepository colorRepository;
    private ProductImageRepository productImageRepository;

    @BeforeEach
    void setUp() {
        ProductPage productPage = new ProductPage();
        ModelMapper modelMapper = new ModelMapper();
        ProductServiceImplementation productServiceUnderTest = new ProductServiceImplementation(productRepository,
                categoryRepository, subCategoryRepository);

    }

    @Test
    void shouldSaveUserThatWillAddProductToCart() {
        final  User user = new User
                ( "austin", "sam", "austin@gmail.com", "male", "27-11-1999", null);

        assertThat(userRepository.findByEmail(user.getEmail())).isNotPresent();

        given(userRepository.save(user)).willAnswer(invocation -> invocation.getArgument(0));
        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldSaveProductThatWillBeAddedToCart() {

        //Product(name=null, price=1000.0, description=Shoes Stilleto, category=null, subCategory=null, sizes=null, colors=[], productImages=[], users=null)

        final Product product = new Product();
        //product.setId(null);
        product.setDescription("Shoes Stilleto");
        product.setPrice(1000);
        product.setCategory(null);
        product.setProductImages(new ArrayList<>());
        product.setColors(new ArrayList<>());

        assertThat(productRepository.findById(product.getId())).isNotPresent();

        given(productRepository.save(product)).willAnswer(invocation -> invocation.getArgument(0));

        Product product1 = productRepository.save(product);

        assertThat(product1).isNotNull();
        verify(productRepository).save(any(Product.class));

    }

    @Test
    void savedCartItemShouldReturnNotNull() {

        final Product product = new Product();
        product.setId(1L);
        product.setDescription("Clothes Cottongown");
        product.setPrice(1000);
        product.setCategory(null);
        product.setProductImages(new ArrayList<>());
        product.setColors(new ArrayList<>());

        User user = new User
                ("austin", "sam", "austin@gmail.com", "male", "27-11-1999", null);

        assertThat(productRepository.findById(product.getId())).isNotPresent();
        given(productRepository.save(product)).willAnswer(invocation -> invocation.getArgument(0));

        Product savedProduct = productRepository.save(product);

        verify(productRepository).save(any(Product.class));

        //cart items
        CartItem item = new CartItem();

        //set product quantity
        String productQuantity = "1";

        item.setUser(user);
        item.setQuantity(productQuantity);
        item.setPrice(savedProduct.getPrice());
//        item.setProduct(savedProduct);

        given(cartItemRepository.save(item)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        CartItem savedCartItem = cartItemServiceImplementation.saveCartItem(cartItemRepository.save(item));

        assertEquals("1", savedCartItem.getQuantity());
        assertEquals(1000.0, savedCartItem.getPrice());
//        assertEquals(product, savedCartItem.getProduct());
        assertEquals(user, savedCartItem.getUser());

        //product already exist
        assertThat(savedProduct.getId()).isEqualTo(1L);

        //to add another quantity of the product
        item.setQuantity(String.valueOf(Integer.parseInt(savedCartItem.getQuantity())+1));
        savedCartItem = cartItemServiceImplementation.saveCartItem(cartItemRepository.save(item));
        assertEquals("2", savedCartItem.getQuantity());

        //to add another quantity of the product
        item.setQuantity(String.valueOf(Integer.parseInt(savedCartItem.getQuantity())+1));
        savedCartItem = cartItemServiceImplementation.saveCartItem(cartItemRepository.save(item));
        assertEquals("3", savedCartItem.getQuantity());

        //for another product entirely
        product.setId(2L);
        item.setQuantity(productQuantity);
        item.setPrice(200.0);
        savedCartItem = cartItemServiceImplementation.saveCartItem(cartItemRepository.save(item));
        assertEquals("1", savedCartItem.getQuantity());

        //to add another quantity of the product
        item.setQuantity(String.valueOf(Integer.parseInt(savedCartItem.getQuantity())+1));
        savedCartItem = cartItemServiceImplementation.saveCartItem(cartItemRepository.save(item));
        assertEquals("2", savedCartItem.getQuantity());


    }
}
