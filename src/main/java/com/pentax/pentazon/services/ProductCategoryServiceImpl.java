package com.pentax.pentazon.services;

import com.pentax.pentazon.dtos.ProductCategoryDTO;
import com.pentax.pentazon.exceptions.ProductCategoryException;
import com.pentax.pentazon.models.ProductCategory;
import com.pentax.pentazon.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;


    @Override
    public void createCategory(ProductCategoryDTO category) {
        saveCategory(ProductCategoryDTO.unpackDto(category));
    }

    private ProductCategory saveCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategoryDTO getCategoryById(String id) throws ProductCategoryException {
        return ProductCategoryDTO.packDto(findCategoryById(id));
    }

    private ProductCategory findCategoryById(String id) throws ProductCategoryException {
        Optional<ProductCategory> optional = productCategoryRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ProductCategoryException("No Category found with that id");
        }
    }

    @Override
    public ProductCategoryDTO updateCategory(String categoryId, ProductCategoryDTO updatedProductCategoryDetails) throws ProductCategoryException {
        ProductCategory category = findCategoryById(categoryId);
        if (!category.getName().equals(updatedProductCategoryDetails.getName())) {
            category.setName(updatedProductCategoryDetails.getName());
        }
        return ProductCategoryDTO.packDto(saveCategory(category));
    }

    @Override
    public void deleteCategory(String categoryId) {
        deleteProductCategory(categoryId);
    }

    private void deleteProductCategory(String categoryId) {
        productCategoryRepository.deleteById(categoryId);
    }

    @Override
    public List<ProductCategoryDTO> getAllCategories() {
        List<ProductCategoryDTO> list = new ArrayList<>();
        for (ProductCategory category : getAllProductCategories()) {
            list.add(ProductCategoryDTO.packDto(category));
        }
        return list;
    }

    @Override
    public ProductCategory getProductCategoryById(String id) throws ProductCategoryException {
        return findCategoryById(id);
    }

    private List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }
}
