package com.pentax.pentazon.dtos;

import com.pentax.pentazon.models.Cart;
import com.pentax.pentazon.models.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    @NotNull
    @NotBlank
    private String id;
    @NotNull
    @NotBlank
    private BigDecimal total;
    private Map<String, Item> items;


    public static Cart unpackDTO(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setItems(cartDTO.getItems());
        cart.setTotal(cartDTO.getTotal());
        return cart;
    }

    public static CartDTO packDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setItems(cart.getItems());
        cartDTO.setTotal(cart.getTotal());
        return cartDTO;
    }
}
