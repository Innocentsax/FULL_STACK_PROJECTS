package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.CategoryDto;
import com.decagon.OakLandv1be.entities.Category;
import com.decagon.OakLandv1be.services.CategoryService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Test
    void addNewCategory() {
        try {
            Category category = Category.builder()
                    .name("Bed")
                    .subCategories(new HashSet<>())
                    .build();
            CategoryDto cdt = CategoryDto.builder()
                    .name("Bed")
                    .build();
            when(categoryService.createCategory(cdt)).thenReturn(new ApiResponse<>("Category Created Successfully", true, category));
            String requestBody = mapper.writeValueAsString(cdt);
            mockMvc.perform(post("/api/v1/category/admin/new" )
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isCreated());
        }catch (Exception ce){
            ce.printStackTrace();
        }
    }

    @Test
    void updateCategory() throws Exception {
        CategoryDto cdt = CategoryDto.builder()
                .name("TestCategory")
                .build();
                Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName("TestCategory");
                when(categoryService.editCategory(cdt, 1L ))
                        .thenReturn( new ApiResponse<>("Category Updated successfully", true, updatedCategory));
            String requestBody = mapper.writeValueAsString(cdt);
            mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/category/admin/update/{category_id}", 1L)
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andReturn();

    }

    @Test
    void viewAllCategories() {
        try {
            CategoryDto cdt = CategoryDto.builder()
                    .name("Bed")
                    .build();
            List<CategoryDto> categoryDtoList = new ArrayList<>();
            categoryDtoList.add(cdt);
            when(categoryService.viewAllCategories()).thenReturn(categoryDtoList);
            mockMvc.perform(get("/api/v1/category/view-all")
                            .contentType("application/json"))
                    .andExpect(status().isOk());
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }

    @Test
    void viewASingleCategory() {
        try {
            Category category = Category.builder()
                    .name("Bed")
                    .subCategories(new HashSet<>())
                    .build();
            category.setId(2L);
            when(categoryService.fetchASingleCategory(anyLong())).thenReturn(category);
            mockMvc.perform(get("/api/v1/category/{category_id}", 12L)
                            .contentType("application/json"))
                    .andExpect(status().isOk());
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }

    @Test
    void deleteCategory() {
        try {
            Category category = Category.builder()
                    .name("Table")
                    .build();
            category.setId(2L);
            doNothing().when(categoryService).deleteCategory(2L);
            mockMvc.perform(delete("/api/v1/category/admin/delete/{category_id}", 2L)
                            .contentType("application/json"))
                    .andExpect(status().isNoContent());
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }
}
