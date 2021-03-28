package com.pentax.pentazon.controllers;

import com.pentax.pentazon.dtos.ApiResponse;
import com.pentax.pentazon.dtos.ProductCategoryDTO;
import com.pentax.pentazon.exceptions.ProductCategoryException;
import com.pentax.pentazon.services.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/category")
@Slf4j
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @PostMapping("/new")
    public ResponseEntity<?> newCategory(@RequestBody ProductCategoryDTO categoryDTO){
        productCategoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(new ApiResponse(true, "Category created successfully"), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getCategories(){
        List<ProductCategoryDTO> categories = productCategoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @GetMapping("{categoryId}")
    public ResponseEntity<?> getRoleById(@PathVariable String categoryId){
        try {
                ProductCategoryDTO productCategoryDTO = productCategoryService.getCategoryById(categoryId);
            return new ResponseEntity<>(productCategoryDTO, HttpStatus.OK);
        } catch (ProductCategoryException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
