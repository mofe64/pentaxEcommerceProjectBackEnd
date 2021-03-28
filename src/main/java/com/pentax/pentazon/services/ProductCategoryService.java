package com.pentax.pentazon.services;

import com.pentax.pentazon.dtos.ProductCategoryDTO;
import com.pentax.pentazon.exceptions.ProductCategoryException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductCategoryService {
    void createCategory(ProductCategoryDTO category);
    ProductCategoryDTO getCategoryById(String id) throws ProductCategoryException;
    ProductCategoryDTO updateCategory(String categoryId, ProductCategoryDTO updatedProductCategoryDetails) throws ProductCategoryException;
    void deleteCategory(String categoryId);
    List<ProductCategoryDTO> getAllCategories();
}
