package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.dto.CartInfo;
import com.decagon.fitnessoapp.dto.CartResponse;
import com.decagon.fitnessoapp.model.product.*;
import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.repository.*;
import com.decagon.fitnessoapp.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final PersonRepository personRepository;

    private TangibleProductRepository tangibleProductRepository;

    private IntangibleProductRepository intangibleProductRepository;


    public CartResponse addToCart(Map<Long, Integer> products, PersonDetails personDetails) {

        Person person = personRepository.findPersonByUserName(personDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Name does not Exist"));

        for(Long productId : products.keySet()){
            Optional<IntangibleProduct> intangibleProduct = intangibleProductRepository.findById(productId);
            Optional<TangibleProduct> tangibleProduct = tangibleProductRepository.findById(productId);
            if(tangibleProduct.isPresent()) {
                if(tangibleProduct.get().getStock() == 0){
                    return CartResponse.builder()
                            .message("Out of Stock for now").build();
                }
                else if(tangibleProduct.get().getStock() < products.get(productId)){
                    return CartResponse.builder()
                            .message("Exceeded what is available, currently we have "
                                    + tangibleProduct.get().getStock() + " of " + tangibleProduct.get().getProductName()
                            ).build();
                }

            }else if (intangibleProduct.isEmpty()) {
                return CartResponse.builder()
                        .message("Product with ID: " + productId + " does not exist")
                        .build();
            }
        }

        final String uniqueIdGenerator = RandomString.make(12);
        List<Cart> all = new ArrayList<>();
        List<CartInfo> allInfo = new ArrayList<>();
        for(Map.Entry<Long, Integer> val : products.entrySet() ){
            Cart cart = new Cart();
            CartInfo cartInfo = new CartInfo();
            Optional<IntangibleProduct> intangibleProduct = intangibleProductRepository.findById(val.getKey());
            Optional<TangibleProduct> tangibleProduct = tangibleProductRepository.findById(val.getKey());
            if(tangibleProduct.isPresent()) {
                cartInfo.setProduct(tangibleProduct.get());
            }
            else{
                cartInfo.setProduct(intangibleProduct.get());
            }

            cartInfo.setQuantity(val.getValue());
            cartInfo.setUniqueCartId(uniqueIdGenerator);
            cartInfo.setPersonId(person.getId());


            cart.setProductId(val.getKey());
            cart.setQuantity(val.getValue());
            cart.setUniqueCartId(uniqueIdGenerator);
            cart.setPersonId(person.getId());

            updateTangibleProduct(val.getKey(), val.getValue());
            all.add(cart);
            allInfo.add(cartInfo);
        }

        shoppingCartRepository.saveAll(all);

        return CartResponse.builder()
                .cartData(allInfo).build();
    }


    public CartResponse deleteCart (Long cartId, PersonDetails personDetails){
        Person person = personRepository.findPersonByUserName(personDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Name does not Exist"));
        shoppingCartRepository.deleteById(cartId);
        return CartResponse.builder().message("removed").build();
    }


    public void updateTangibleProduct(Long productId, Integer quantity){
        TangibleProduct tangibleProduct = tangibleProductRepository.findById(productId).orElse(null);
        if(tangibleProduct != null){
            final Long stock = tangibleProduct.getStock();
            tangibleProduct.setStock(stock - Long.valueOf(quantity));
            tangibleProductRepository.save(tangibleProduct);
        }
    }

    public CartResponse getCartById(Long cartId) {
        return CartResponse.builder().cart(shoppingCartRepository.getById(cartId)).build();
    }

    public CartResponse viewCartItems() {
        return CartResponse.builder().cartList(shoppingCartRepository.findAll()).build();
    }
}
