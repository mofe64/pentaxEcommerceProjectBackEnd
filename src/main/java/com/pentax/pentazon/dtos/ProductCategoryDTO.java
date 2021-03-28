package com.pentax.pentazon.dtos;

import com.pentax.pentazon.models.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDTO {
    private String id;
    @NotNull
    @NotBlank(message = "Category name cannot be blank")
    private String name;

    public static ProductCategory unpackDto(ProductCategoryDTO productCategoryDTO){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(productCategoryDTO.getName());
        return productCategory;
    }
    public static ProductCategoryDTO packDto(ProductCategory productCategory){
        ProductCategoryDTO productCategoryDTO= new ProductCategoryDTO();
        productCategoryDTO.setId(productCategory.getId());
        productCategoryDTO.setName(productCategory.getName());
        return productCategoryDTO;
    }
}
