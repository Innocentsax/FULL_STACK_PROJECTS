package com.example.cedarxpressliveprojectjava010.service.impl;
import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Category;
import com.example.cedarxpressliveprojectjava010.entity.ImageUrl;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.SubCategory;
import com.example.cedarxpressliveprojectjava010.exception.ProductNotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.CategoryRepository;
import com.example.cedarxpressliveprojectjava010.repository.ImageUrlRepository;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.repository.SubCategoryRepository;
import com.example.cedarxpressliveprojectjava010.service.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import java.util.Optional;
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepository productRepository;
    private final ImageUrlRepository imageUrlRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ResponseEntity<Product> createProduct(ProductDto productDto) {
        Product newProduct = new Product();
        Optional<Category> productCategory = categoryRepository.findCategoryByCategoryName(productDto.getCategory());
        Category category;
        if (productCategory.isEmpty()) {
            category = Category.builder().categoryName(productDto.getCategory()).build();
            categoryRepository.save(category);
            newProduct.setCategory(category);
        } else {
            category = productCategory.get();
            newProduct.setCategory(category);
        }
        Optional<SubCategory>  productSubCategory = subCategoryRepository.findSubCategoryBySubCategoryName(productDto.getSubCategory());
        if (productSubCategory.isEmpty()){
            SubCategory subCategory = SubCategory.builder().subCategoryName(productDto.getSubCategory().toLowerCase()).build();
            subCategory.setCategory(category);
            subCategoryRepository.save(subCategory);
            newProduct.setSubCategory(subCategory);
        } else {
            newProduct.setSubCategory(productSubCategory.get());
        }
        newProduct.setProductName(productDto.getProductName().toLowerCase());
        newProduct.setDescription(productDto.getDescription().toLowerCase());
        newProduct.setPrice(productDto.getPrice());
        newProduct = productRepository.save(newProduct);
        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Product> addProductImage(String img, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("product with ID "+productId+" Does not Exist"));
        ImageUrl imgUrl = ImageUrl.builder().img(img).build();
        imgUrl = imageUrlRepository.save(imgUrl);
        product.getImages().add(imgUrl);
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found!"));
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product with id "+ id +" deleted successfully");
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product with ID" + " " + productId +"does not exist" ));
        if(productDto.getCategory() != null || productDto.getCategory().isBlank()) {
            Optional<Category> categoryOps = categoryRepository.findCategoryByCategoryName(productDto.getCategory());
            if(categoryOps.isPresent()) {
                Category category = categoryOps.get();
                product.setCategory(category);
            }
        }
        if(productDto.getSubCategory() != null || productDto.getSubCategory().isBlank()) {
            Optional<SubCategory> subcategoryOps = subCategoryRepository.findSubCategoryBySubCategoryName(productDto.getSubCategory());
            if(subcategoryOps.isPresent()) {
                SubCategory subCategory = subcategoryOps.get();
                product.setSubCategory(subCategory);
            }
        }

        if(productDto.getProductName() != null || productDto.getProductName().isBlank()) {
            product.setProductName(productDto.getProductName());
        }

        if(productDto.getPrice() != 0) {
            product.setPrice(productDto.getPrice());
        }


        if(productDto.getDescription() != null || productDto.getDescription().isBlank()) {
            product.setDescription(productDto.getDescription());
        }


        Product updatedProduct = productRepository.save(product);


        ProductDto newUpdatedProduct = new ProductDto();
        newUpdatedProduct.setProductName(updatedProduct.getProductName());
        newUpdatedProduct.setDescription(updatedProduct.getDescription());
        newUpdatedProduct.setPrice(updatedProduct.getPrice());
        newUpdatedProduct.setSubCategory(updatedProduct.getSubCategory().getSubCategoryName());
        newUpdatedProduct.setCategory(updatedProduct.getCategory().getCategoryName());

        return newUpdatedProduct;
    }
}