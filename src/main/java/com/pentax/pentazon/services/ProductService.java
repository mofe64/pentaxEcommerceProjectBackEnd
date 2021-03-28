package com.pentax.pentazon.services;


import com.pentax.pentazon.dtos.ProductDTO;
import com.pentax.pentazon.exceptions.ProductCategoryException;
import com.pentax.pentazon.exceptions.ProductException;
import com.pentax.pentazon.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    public void addProduct(ProductDTO productDTO);
    public void removeProduct(String productId) throws ProductException;
    public ProductDTO findProductById(String productId) throws ProductException;
    public List<ProductDTO> getAllProducts();
    public ProductDTO updateProduct(String productId ,ProductDTO updatedProductDetails) throws ProductException;
    public Product findProduct(String productId) throws ProductException;
    public List<ProductDTO> getAllProductsInACategory(String categoryId);
}
