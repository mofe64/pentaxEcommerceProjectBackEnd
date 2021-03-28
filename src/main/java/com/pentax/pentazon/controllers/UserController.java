package com.pentax.pentazon.controllers;

import com.pentax.pentazon.dtos.ApiResponse;
import com.pentax.pentazon.dtos.CartDTO;
import com.pentax.pentazon.dtos.UserDTO;
import com.pentax.pentazon.exceptions.CartException;
import com.pentax.pentazon.exceptions.ProductException;
import com.pentax.pentazon.exceptions.UserException;
import com.pentax.pentazon.models.Address;
import com.pentax.pentazon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId){
        try {
            UserDTO userDTO = userService.findUserById(userId);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (UserException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("{userId}/cart")
    public ResponseEntity<?> getUserCart(@PathVariable String userId) {
        try {
            CartDTO cartDTO = userService.getUserCart(userId);
            return new ResponseEntity<>(cartDTO, HttpStatus.OK);
        } catch (UserException | CartException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage())
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{userId}/cart/add/{productId}/{quantity}")
    public ResponseEntity<?> addProductToCart(@PathVariable String userId, @PathVariable String productId, @PathVariable String quantity) {
        try {
            int quantityValue = Integer.parseInt(quantity);
            userService.addProductToCart(productId, userId, quantityValue);
            return new ResponseEntity<>(new ApiResponse(true, "Product Added Successfully"), HttpStatus.OK);
        } catch (ProductException | UserException | CartException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{userId}/cart/remove/{productId}/{quantity}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable String userId, @PathVariable String productId, @PathVariable String quantity) {
        try {
            int quantityValue = Integer.parseInt(quantity);
            ;
            userService.removeProductFromCart(productId, userId, quantityValue);
            return new ResponseEntity<>(new ApiResponse(true, "Product Removed Successfully"), HttpStatus.OK);
        } catch (UserException | CartException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("{userId}/addresses/add")
    public ResponseEntity<?> addAddress(@RequestBody Address address, @PathVariable String userId) {
        try {
            userService.addAddress(userId, address);
            return new ResponseEntity<>(new ApiResponse(true, "Address added successfully"),HttpStatus.OK );
        } catch (UserException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("{userId}/addresses")
    public ResponseEntity<?> getAddresses(@PathVariable String userId) {
        try {
            List<Address> addresses = userService.getUserAddresses(userId);
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }



}
