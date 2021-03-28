package com.pentax.pentazon.repository;

import com.pentax.pentazon.models.Address;
import com.pentax.pentazon.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(String id);
    void deleteUserById(String id);

}
