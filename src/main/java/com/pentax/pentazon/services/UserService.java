package com.pentax.pentazon.services;

import com.pentax.pentazon.dtos.CartDTO;
import com.pentax.pentazon.dtos.LoginRequest;
import com.pentax.pentazon.dtos.Token;
import com.pentax.pentazon.dtos.UserDTO;
import com.pentax.pentazon.exceptions.AuthenticationException;
import com.pentax.pentazon.exceptions.CartException;
import com.pentax.pentazon.exceptions.ProductException;
import com.pentax.pentazon.exceptions.UserException;
import com.pentax.pentazon.models.Address;
import com.pentax.pentazon.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public interface UserService {
    UserDTO registerBuyer(UserDTO buyerDetails) throws UserException;

    UserDTO registerSeller(UserDTO sellerDetails) throws UserException;

    CartDTO getUserCart(String userId) throws UserException, CartException;

    UserDTO findUserById(String userId) throws UserException;

    void addAddress(String userId, Address address) throws UserException;

    void addProductToCart(String productId, String userId, int quantity) throws UserException, ProductException, CartException;

    void removeProductFromCart(String productId, String userId, int quantity) throws UserException, CartException;

    List<Address> getUserAddresses(String userId) throws UserException;
}