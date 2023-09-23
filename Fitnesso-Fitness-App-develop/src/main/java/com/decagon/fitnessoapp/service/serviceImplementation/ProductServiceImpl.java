package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.dto.ProductRequestDto;
import com.decagon.fitnessoapp.dto.ProductResponseDto;
import com.decagon.fitnessoapp.dto.UserProductDto;
import com.decagon.fitnessoapp.model.product.IntangibleProduct;
import com.decagon.fitnessoapp.model.product.TangibleProduct;
import com.decagon.fitnessoapp.repository.IntangibleProductRepository;
import com.decagon.fitnessoapp.repository.TangibleProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements com.decagon.fitnessoapp.service.ProductService {

    private final IntangibleProductRepository intangibleProductRepository;
    private final TangibleProductRepository tangibleProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(IntangibleProductRepository intangibleProductRepository, TangibleProductRepository tangibleProductRepository, ModelMapper modelMapper) {
        this.intangibleProductRepository = intangibleProductRepository;
        this.tangibleProductRepository = tangibleProductRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponseDto viewProductDetails(Long id, String productType) {
        ProductResponseDto productDetailsResponse = new ProductResponseDto();

        if(productType.equals("PRODUCT")){
            modelMapper.map(tangibleProductRepository.getById(id), productDetailsResponse );
            productDetailsResponse.setProductType("PRODUCT");
            if(productDetailsResponse.getStock() == 0) {
                productDetailsResponse.setImage("Sold Out");
            }

        }else if(productType.equals("SERVICE")){
            modelMapper.map(intangibleProductRepository.getById(id), productDetailsResponse);
            productDetailsResponse.setProductType("SERVICE");
            if(productDetailsResponse.getStock() == 0) {
                productDetailsResponse.setImage("Sold Out");
            }

        }
        return productDetailsResponse;
    }

    @Override
    public ProductResponseDto addProduct(ProductRequestDto requestDto) throws IOException {
        ProductResponseDto responseDto;
        ProductRequestDto productDto = new ProductRequestDto();

        productDto.setCategory(requestDto.getCategory().toUpperCase());
        productDto.setProductName(requestDto.getProductName().toUpperCase());
        productDto.setPrice(requestDto.getPrice());
        productDto.setDescription(requestDto.getDescription());
        productDto.setProductType(requestDto.getProductType());
        productDto.setImage(requestDto.getImage());
        productDto.setMonthlySubscription(requestDto.getMonthlySubscription());
        productDto.setQuantity(0);
        productDto.setStock(Long.valueOf(requestDto.getQuantity()));


        if (productDto.getProductType().equals("PRODUCT")) {
            TangibleProduct tangibleProduct = tangibleProductRepository.findFirstByProductNameAndDescriptionAndCategoryAndPrice(requestDto.getProductName().toUpperCase(), requestDto.getDescription(), requestDto.getCategory().toUpperCase(), requestDto.getPrice()).orElse(null);

            if(tangibleProduct != null) {
                tangibleProduct.setStock(tangibleProduct.getStock() + requestDto.getQuantity());
                tangibleProductRepository.save(tangibleProduct);
                return modelMapper.map(tangibleProduct, ProductResponseDto.class);
            }

            TangibleProduct newProduct;

            newProduct = tangibleProductRepository.save(modelMapper.map(productDto, TangibleProduct.class));
            responseDto = modelMapper.map(newProduct, ProductResponseDto.class);
           return responseDto;

        } else if (productDto.getProductType().equals("SERVICE")) {
            IntangibleProduct newProduct;

            newProduct = intangibleProductRepository.save(modelMapper.map(productDto, IntangibleProduct.class));
//            newProduct.setMonthlySubscription(requestDto.getQuantity());
            newProduct.setMonthlySubscription(requestDto.getMonthlySubscription());
            final IntangibleProduct savedService = intangibleProductRepository.save(newProduct);
            responseDto = modelMapper.map(savedService, ProductResponseDto.class);
            return responseDto;
        }
        throw new IllegalStateException("Check if all fields were filled properly");
    }



    @Override
    public ProductResponseDto deleteProduct(Long productId) {
        boolean isTangiblePresent = tangibleProductRepository.findById(productId).isPresent();
        boolean isIntangiblePresent = intangibleProductRepository.findById(productId).isPresent();
        if(isTangiblePresent ) {
            TangibleProduct deletedProduct =  tangibleProductRepository.getById(productId);
            tangibleProductRepository.deleteById(productId);
            return modelMapper.map(deletedProduct, ProductResponseDto.class);
        }
        if(isIntangiblePresent) {
            IntangibleProduct deletedProduct =  intangibleProductRepository.getById(productId);
            intangibleProductRepository.deleteById(productId);
            return modelMapper.map(deletedProduct, ProductResponseDto.class);
        }
        throw new IllegalArgumentException("Check if the product is available for delete");
    }


    @Override
    public Page<TangibleProduct> getAllProduct(int pageSize, int pageNumber) {
        Page<TangibleProduct> products = tangibleProductRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return  products;
    }

    @Override
    public Page<IntangibleProduct> getAllServices(int pageSize, int pageNumber) {
        Page<IntangibleProduct> products = intangibleProductRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return  products;
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto) {
        boolean isTangiblePresent = tangibleProductRepository.findById(productId).isPresent();
        boolean isIntangiblePresent = intangibleProductRepository.findById(productId).isPresent();
        if(isTangiblePresent){
            TangibleProduct product = tangibleProductRepository.getById(productId);
            modelMapper.map(requestDto, product);
            TangibleProduct updatedProduct = tangibleProductRepository.save(product);
            return modelMapper.map(updatedProduct, ProductResponseDto.class);
        }
        if(isIntangiblePresent){
            IntangibleProduct product = intangibleProductRepository.getById(productId);
            modelMapper.map(requestDto, product);
            IntangibleProduct updatedProduct = intangibleProductRepository.save(product);
            return modelMapper.map(updatedProduct, ProductResponseDto.class);
        }
        throw new IllegalArgumentException("Check if the product is available for an update");
    }


    @Override
    public ProductResponseDto getProduct(Long productId) {
        boolean isTangiblePresent = tangibleProductRepository.findById(productId).isPresent();
        boolean isIntangiblePresent = intangibleProductRepository.findById(productId).isPresent();
        if (isTangiblePresent) {
            ProductResponseDto responseDto = new ProductResponseDto();
            modelMapper.map(tangibleProductRepository.getById(productId), responseDto);
            return responseDto;
        }
        if (isIntangiblePresent) {
            ProductResponseDto responseDto = new ProductResponseDto();
            modelMapper.map(intangibleProductRepository.getById(productId), responseDto);
            return responseDto;
        }
        throw new IllegalArgumentException("Check if the Id is correct");
    }

    @Override
    public Page<UserProductDto> getAllProducts(int pageNumber) {
        List<UserProductDto> dtoList = getDtoList();
        int pageSize = 10;
        int skipCount = (pageNumber - 1) * pageSize;
        List<UserProductDto> activityPage = dtoList
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());
        Pageable productPage = PageRequest.of(pageNumber, pageSize, Sort.by("productName").ascending());
        return new PageImpl<>(activityPage, productPage, dtoList.size());
    }

    private List<UserProductDto> getDtoList() {
        List<IntangibleProduct> intangibleProducts = intangibleProductRepository.findAll();
        List<TangibleProduct> tangibleProducts = tangibleProductRepository.findAll();

        List<UserProductDto> intangibleDtos = intangibleProducts.stream()
                .map(x -> modelMapper.map(x, UserProductDto.class))
                .collect(Collectors.toList());

        List<UserProductDto> tangibleDtos = tangibleProducts.stream()
                .map(x -> modelMapper.map(x, UserProductDto.class))
                .collect(Collectors.toList());

        List<UserProductDto> productDtos = new ArrayList<>(intangibleDtos);
        productDtos.addAll(tangibleDtos);

        Collections.sort(productDtos);
        return productDtos;
    }
    @Override
    public List<UserProductDto> searchAllProducts() {
        return getDtoList();
    }
    @Override
    public List<?> searchProduct(String text) {
        List<ProductResponseDto> searchResult = new ArrayList<>();
        String freeText = text.toUpperCase();
        if (freeText != null) {
            List<TangibleProduct> tangibleProducts = tangibleProductRepository.findTangibleProductByCategoryOrProductNameOrByDescription(freeText);

            List<IntangibleProduct> intangibleProducts = intangibleProductRepository.findIntangibleProductByCategoryOrProductNameOrByDescription(freeText);

            if(tangibleProducts.size() > 0){
                tangibleProducts.forEach(product->{
                    ProductResponseDto productResponseDto = modelMapper.map(product,ProductResponseDto.class);
                      searchResult.add(productResponseDto);
                });
            }
            if(intangibleProducts.size() > 0){
                intangibleProducts.forEach(product->{
                    ProductResponseDto productResponseDto1 = modelMapper.map(product,ProductResponseDto.class);
                    searchResult.add(productResponseDto1);
                });
            }

        }
        return searchResult;
    }

    @Override
    public List<UserProductDto> getProductsNP() {
        List<TangibleProduct> productList = tangibleProductRepository.findAll();
        final List<UserProductDto> productDtos = productList.stream()
                .map(x -> modelMapper.map(x, UserProductDto.class))
                .collect(Collectors.toList());
        Collections.sort(productDtos);
        return productDtos;
    }

    @Override
    public List<UserProductDto> getServicesNP() {
        List<IntangibleProduct> productList = intangibleProductRepository.findAll();
        final List<UserProductDto> productDtos = productList.stream()
                .map(x -> modelMapper.map(x, UserProductDto.class))
                .collect(Collectors.toList());
        Collections.sort(productDtos);
        return productDtos;
    }

    @Override
    public List<UserProductDto> getAllProductsNP() {
        List<UserProductDto> dtoList = getDtoList();
        int pageSize = 10;
        int pageNumber = 1;
        int skipCount = (pageNumber - 1) * pageSize;
        List<UserProductDto> activityPage = dtoList
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());

        return dtoList;
    }
}
