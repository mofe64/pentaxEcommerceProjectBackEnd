package com.pentax.pentazon.repository;

import com.pentax.pentazon.models.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductCategoryRepository extends MongoRepository<ProductCategory, String> {

}
