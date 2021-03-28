package com.pentax.pentazon.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Product product;
    private int quantity =1;
    private BigDecimal itemTotal;

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
        itemTotal = product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
        itemTotal = product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }


}
