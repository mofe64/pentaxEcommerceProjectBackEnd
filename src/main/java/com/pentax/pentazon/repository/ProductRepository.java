package com.pentax.pentazon.repository;

import com.pentax.pentazon.models.ProductCategory;
import com.pentax.pentazon.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findProductByPriceGreaterThan(BigDecimal price);
    Optional<Product> findProductById(String id);
    List<Product> findProductByDescriptionContaining(String phrase);
    List<Product> findProductsByCategory(ProductCategory productCategory);
}
