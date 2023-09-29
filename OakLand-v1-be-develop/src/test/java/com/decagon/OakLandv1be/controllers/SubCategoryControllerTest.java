package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.SubCategoryDto;
import com.decagon.OakLandv1be.entities.Category;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.services.SubCategoryService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubCategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class SubCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SubCategoryController subCategoryController;

    @MockBean
    private SubCategoryService subCategoryService;

    @Test
    void addNewSubCategory() {
        try {
            Category category = new Category("Tester", "image.com", new HashSet<>());
            category.setId(3L);
            SubCategory subCategory = SubCategory.builder()
                    .name("Bed")
                    .imageUrl("subcat.com")
                    .category(category)
                    .build();
            SubCategoryDto subCategoryDto = SubCategoryDto.builder()
                    .name("Bed")
                    .imageUrl("subcat.com")
                    .id(2L)
                    .size(1)
                    .build();
            when(subCategoryService.createSubCategory(subCategoryDto, 3L))
                    .thenReturn(new ApiResponse<>("SubCategory Created Successfully", true, subCategory));
            String requestBody = mapper.writeValueAsString(subCategoryDto);
            mockMvc.perform(post("/api/v1/subcategory/admin/new/"+ category.getId())
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isCreated())
                    .andReturn();
        }catch (Exception ce) {
            ce.printStackTrace();
        }
    }

    @Test
    void viewAllSubCategories() {
        try {
            SubCategoryDto sub = SubCategoryDto.builder()
                    .name("Bed")
                    .build();
            List<SubCategoryDto> subCategoryDtoList = new ArrayList<>();
            subCategoryDtoList.add(sub);
            when(subCategoryService.viewAllSubCategories()).thenReturn(subCategoryDtoList);
            mockMvc.perform(get("/api/v1/subcategory/view-all")
                            .contentType("application/json"))
                    .andExpect(status().isOk());
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }

    @Test
    void updateSubCategory() throws Exception {
        SubCategory updatedSubCategory = new SubCategory();
        updatedSubCategory.setId(2L);
        updatedSubCategory.setName("TestCategory");
        SubCategoryDto sub = SubCategoryDto.builder()
                .name("TestSubCategory")
                .id(2L)
                .size(1)
                .imageUrl("test.com")
                .build();
        when(subCategoryService.editSubCategory(sub, 2L ))
                .thenReturn( new ApiResponse<>("SubCategory Updated successfully", true, updatedSubCategory));
        String requestBody = mapper.writeValueAsString(sub);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/subcategory/admin/update/"+ updatedSubCategory.getId())
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteSubCategory() {
        try {
            SubCategory subCategory = SubCategory.builder()
                    .name("Table")
                    .build();
            subCategory.setId(2L);
            doNothing().when(subCategoryService).deleteSubCategory(2L);
            mockMvc.perform(delete("/api/v1/subcategory/admin/delete/{subCategoryId}", 2L)
                            .contentType("application/json"))
                    .andExpect(status().isNoContent());
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }

    @Test
    void viewAllSubcategoriesInACategory() {
        try {
            Category category = new Category("Tester", "image.com", new HashSet<>());
            category.setId(3L);
            SubCategoryDto sub = SubCategoryDto.builder()
                    .name("Bed")
                    .build();
            Set<SubCategoryDto> subCategoryDtos = new HashSet<>();
            subCategoryDtos.add(sub);
            when(subCategoryService.ViewSubCategoryByCategory(3L)).thenReturn(subCategoryDtos);
            mockMvc.perform(get("/api/v1/subcategory/viewByCategory/"+ category.getId())
                            .contentType("application/json"))
                    .andExpect(status().isOk());
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }
}