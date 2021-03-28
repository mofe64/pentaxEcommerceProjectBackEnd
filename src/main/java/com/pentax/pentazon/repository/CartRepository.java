package com.pentax.pentazon.repository;

import com.pentax.pentazon.models.Cart;
import com.pentax.pentazon.models.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findCartById(String Id);

}
