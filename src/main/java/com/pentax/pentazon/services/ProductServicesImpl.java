package com.pentax.pentazon.services;

import com.pentax.pentazon.dtos.ProductDTO;
import com.pentax.pentazon.exceptions.ProductCategoryException;
import com.pentax.pentazon.exceptions.ProductException;
import com.pentax.pentazon.models.Product;
import com.pentax.pentazon.models.ProductCategory;
import com.pentax.pentazon.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServicesImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCategoryService productCategoryService;

    @Override
    public void addProduct(ProductDTO productDTO) {
        Product productToSave = ProductDTO.unpackDTO(productDTO);
        addNewProduct(productToSave);

    }

    private void addNewProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void removeProduct(String productId) throws ProductException {
        Product productToRemove = findAProductById(productId);
        removeProduct(productToRemove);

    }

    private void removeProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public ProductDTO findProductById(String productId) throws ProductException {
        return ProductDTO.packDTO(findAProductById(productId));
    }

    private Product findAProductById(String productId) throws ProductException {
        Optional<Product> productOptional = productRepository.findProductById(productId);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new ProductException("No product found with that id");
        }
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : getAllProductsFromDb()) {
            productDTOS.add(ProductDTO.packDTO(product));
        }
        return productDTOS;
    }

    private List<Product> getAllProductsFromDb() {
        return productRepository.findAll();
    }

    @Override
    public ProductDTO updateProduct(String productId, ProductDTO updatedProductDetails) throws ProductException {
        Product productToUpdate = findAProductById(productId);
        if (!productToUpdate.getDescription().equals(updatedProductDetails.getDescription())) {
            productToUpdate.setDescription(updatedProductDetails.getDescription());
        }
        if (!productToUpdate.getPrice().toString().equals(updatedProductDetails.getPrice())) {
            productToUpdate.setPrice(new BigDecimal(updatedProductDetails.getPrice()));
        }
        if (!productToUpdate.getImage().equals(updatedProductDetails.getImage())) {
            productToUpdate.setImage(updatedProductDetails.getImage());
        }
        Product updatedProduct = saveProduct(productToUpdate);
        return ProductDTO.packDTO(updatedProduct);
    }

    @Override
    public Product findProduct(String productId) throws ProductException {
        return findAProductById(productId);
    }

    @Override
    public List<ProductDTO> getAllProductsInACategory(String categoryId) {
        List<ProductDTO> products = new ArrayList<>();
        for (Product product: getProductInCategory(categoryId)){
            products.add(ProductDTO.packDTO(product));
        }
        return products;
    }
    private List<Product> getProductInCategory(String productCategoryId){
       return productRepository.findProductsByCategoryId(productCategoryId);
    }

    private Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
