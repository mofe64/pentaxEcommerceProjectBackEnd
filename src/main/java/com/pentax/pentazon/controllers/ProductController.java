package com.pentax.pentazon.controllers;


import com.pentax.pentazon.dtos.ApiResponse;
import com.pentax.pentazon.dtos.ProductDTO;
import com.pentax.pentazon.exceptions.ProductCategoryException;
import com.pentax.pentazon.exceptions.ProductException;
import com.pentax.pentazon.models.Product;
import com.pentax.pentazon.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("DuplicatedCode")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v1/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @PostMapping("/new")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO) {
//        System.out.println(productDTO);
        log.info("Product DTO : {}", productDTO);
        productService.addProduct(productDTO);
        return new ResponseEntity<>(new ApiResponse(true, "Product created successfully"),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<?> removeProduct(@Valid @PathVariable String productId) {
//        System.out.println(productId);
        log.info("productId : {}", productId);
        try {
            productService.removeProduct(productId);
            return new ResponseEntity<>(new ApiResponse(true, "product removed successfully"),
                    HttpStatus.NO_CONTENT);

        } catch (ProductException productException) {
            return new ResponseEntity<>(new ApiResponse(false, productException.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("{productId}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO updatedProductDetails, @PathVariable String productId) {
//        System.out.println(updatedProductDetails);
        log.info("Product DTO : {} \n productID : {}", updatedProductDetails, productId);
        try {
            ProductDTO updatedProduct = productService.updateProduct(productId, updatedProductDetails);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

        } catch (ProductException productException) {
            return new ResponseEntity<>(new ApiResponse(false, productException.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/all/{categoryId}")
    public ResponseEntity<?> getAllProductsInACategory(@PathVariable String categoryId) {
        List<ProductDTO> products = productService.getAllProductsInACategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<?> findProductById(@RequestBody @PathVariable String productId) {
//        System.out.println(productId);
        log.info("productId : {}", productId);
        try {
            ProductDTO productDTO = productService.findProductById(productId);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (ProductException productException) {
            return new ResponseEntity<>(new ApiResponse(false, productException.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }));
        return errors;
    }

}