package com.pentax.pentazon.dtos;

import com.pentax.pentazon.models.Product;
import com.pentax.pentazon.models.Review;
import com.pentax.pentazon.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    @NotNull
    @NotBlank(message = "Please enter a product description")
    private String description;
    @NotNull
    @NotBlank(message = "Please enter a product name")
    private String name;
    @NotNull
    @NotBlank(message = "please enter a price")
    private String price;
    @NotNull
    @NotBlank(message = "Please provide a product Image")
    private String image;
    @NotNull
    @NotBlank(message = "Please provide a category Id")
    private String categoryId;
    private List<Review> reviews = new ArrayList<>();


    public static Product unpackDTO(ProductDTO productDTO){
        Product product = new Product();
       product.setDescription(productDTO.getDescription());
       product.setPrice(new BigDecimal(productDTO.getPrice()));
       product.setImage(productDTO.getImage());
       product.setName(productDTO.getName());
       product.setCategoryId(productDTO.getCategoryId());
        return product;
    }

    public static ProductDTO packDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice().toPlainString());
        productDTO.setImage(productDTO.getImage());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategoryId());
        productDTO.setReviews(product.getReviews());
        return productDTO;

    }
}



