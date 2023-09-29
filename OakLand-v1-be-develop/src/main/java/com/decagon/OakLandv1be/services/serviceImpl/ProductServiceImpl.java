package com.decagon.OakLandv1be.services.serviceImpl;

import com.cloudinary.utils.ObjectUtils;
import com.decagon.OakLandv1be.config.CloudinaryConfig;
import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.exceptions.*;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.repositries.SubCategoryRepository;

import com.decagon.OakLandv1be.services.ProductService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.Mapper;
import com.decagon.OakLandv1be.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepository productRepository;

    private final CloudinaryConfig cloudinaryConfig;

//    @Override
//    public Page<ProductCustResponseDto> productWithPaginationAndSorting(Integer offset, Integer size, String field) {
//        return null;
//    }

    public ProductCustResponseDto fetchASingleProduct(Long product_id) {
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ProductNotFoundException("This product was not found"));
        return ProductCustResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .color(product.getColor())
                .description(product.getDescription())
                .subCategory(product.getSubCategory())
                .build();
    }

    @Override
    public List<ProductCustResponseDto> fetchAllProducts() {
        List<Product> products = productRepository.findAll();
        System.out.println("Products: " + products);
        List<ProductCustResponseDto> productCustResponseDtoList= new ArrayList<>();
        products.forEach(product -> {
            ProductCustResponseDto productCustResponseDto = new ProductCustResponseDto();
            productCustResponseDto.setName(product.getName());
            productCustResponseDto.setPrice(product.getPrice());
            productCustResponseDto.setImageUrl(product.getImageUrl());
            productCustResponseDto.setColor(product.getColor());
            productCustResponseDto.setDescription(product.getDescription());
            productCustResponseDtoList.add(productCustResponseDto);
        });
        return productCustResponseDtoList;
    }

    @Override
    public ApiResponse<Page<ProductResponseDto>> getAllProducts(Integer pageNo, Integer pageSize, String sortBy, boolean isAscending) {
        List<Product> products = productRepository.findAll();

        List<ProductResponseDto> productCustResponseDtos = new ArrayList<>();

        products.forEach(product -> {
            productCustResponseDtos.add(
                    ProductResponseDto.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .price(UserUtil.formatToLocale(BigDecimal.valueOf(product.getPrice())))
                            .sales(product.getSales())
                            .imageUrl(product.getImageUrl())
                            .availableQty(product.getAvailableQty())
                            .subCategory(product.getSubCategory())
                            .color(product.getColor())
                            .description(product.getDescription())
                            .build()
            );
        });
        Collections.sort(productCustResponseDtos, Comparator.comparing(ProductResponseDto::getId, Comparator.reverseOrder()));
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);

        int minimum = pageNo*pageSize;
        int max = Math.min(pageSize * (pageNo + 1), products.size());

        Page<ProductResponseDto> page = new PageImpl<>
                (productCustResponseDtos.subList(minimum, max), pageable,
                        productCustResponseDtos.size());

        return new ApiResponse<>("Pages",  page, HttpStatus.OK);

    }

    @Override
    public Page<ProductCustResponseDto> productWithPaginationAndSorting(Integer page, Integer size, String sortingField,boolean isAscending) {
        return productRepository.findAll(PageRequest.of(page, size,
                isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField)).map(Mapper::productToProductResponseDto);
    }

    @Override
    public String uploadProductImage(long productId, MultipartFile image) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("Product not found"));

        String productImageUrl = uploadImage(image);
        product.setImageUrl(productImageUrl);
        productRepository.save(product);
        return productImageUrl;
    }

    @Override
    public List<ProductCustResponseDto> viewNewArrivalProducts() {
        return productRepository.findProductByCreatedAtDesc().stream()
                .map(this::productResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductCustResponseDto> viewBestSellingProducts() {
        return productRepository.findProductsBySalesDesc().stream()
                .map(this::productResponseMapper)
                .collect(Collectors.toList());
    }

    public void deleteProductImage(String publicUrl){
        try {
            cloudinaryConfig.cloudinary().uploader().destroy(publicUrl, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<Page<ProductResponseDto>> getAllProductsBySubCategory(Long subCategoryId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending) {
        List<Product> products = productRepository.findAllBySubCategoryId(subCategoryId);

        List<ProductResponseDto> subcategoryProductDtos = new ArrayList<>();

        products.forEach(product -> {
            subcategoryProductDtos.add(
                    ProductResponseDto.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .price(UserUtil.formatToLocale(BigDecimal.valueOf(product.getPrice())))
                            .sales(product.getSales())
                            .imageUrl(product.getImageUrl())
                            .availableQty(product.getAvailableQty())
                            .subCategory(product.getSubCategory())
                            .color(product.getColor())
                            .description(product.getDescription())
                            .build()
            );
        });
        Collections.sort(subcategoryProductDtos, Comparator.comparing(ProductResponseDto::getId, Comparator.reverseOrder()));
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);

        int minimum = pageNo*pageSize;
        int max = Math.min(pageSize * (pageNo + 1), products.size());

        Page<ProductResponseDto> page = new PageImpl<>
                (subcategoryProductDtos.subList(minimum, max), pageable,
                        subcategoryProductDtos.size());

        return new ApiResponse<>("Pages",  page, HttpStatus.OK);
    }

    public String uploadImage(MultipartFile image) throws IOException {
        try {
            File uploadedFile = convertMultiPartToFile(image);
            Map uploadResult = cloudinaryConfig.cloudinary().uploader().upload(uploadedFile, ObjectUtils.asMap("use_filename", true, "unique_filename", true));
            boolean isDeleted = uploadedFile.delete();
            if (isDeleted){
                log.info("File successfully deleted");
            }else
                log.info("File doesn't exist");
            return  uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }


    private File convertMultiPartToFile(MultipartFile image) throws IOException {
        String file =  image.getOriginalFilename();
        if (file == null) throw new AssertionError();
        File convFile = new File(file);
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(image.getBytes());
        fos.close();
        return convFile;
    }
    protected ProductCustResponseDto productResponseMapper(Product product){
        return ProductCustResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .color(product.getColor())
                .description(product.getDescription())
                .build();
    }

    @Override
    public String uploadProductImageFileWithoutId(MultipartFile productImage) throws IOException {
        return uploadImage(productImage);
    }

}