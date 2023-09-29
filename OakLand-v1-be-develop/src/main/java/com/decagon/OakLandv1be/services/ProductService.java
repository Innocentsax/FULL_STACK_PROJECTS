package com.decagon.OakLandv1be.services;
import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductCustResponseDto fetchASingleProduct(Long product_id);

    Page<ProductCustResponseDto> productWithPaginationAndSorting(Integer page, Integer size, String sortingField,boolean isAscending);


    public List<ProductCustResponseDto> fetchAllProducts();
    String uploadProductImage(long productId, MultipartFile image) throws IOException;
    List<ProductCustResponseDto> viewNewArrivalProducts();
    List<ProductCustResponseDto> viewBestSellingProducts();
    void deleteProductImage(String publicUrl);


    ApiResponse<Page<ProductResponseDto>> getAllProducts(Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);
    ApiResponse<Page<ProductResponseDto>> getAllProductsBySubCategory(Long subCategoryId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);
    String uploadProductImageFileWithoutId(MultipartFile productImage) throws IOException;
}
