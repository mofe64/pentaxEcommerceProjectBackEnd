package com.pentax.pentazon.services;

import com.pentax.pentazon.dtos.CartDTO;
import com.pentax.pentazon.exceptions.CartException;
import com.pentax.pentazon.exceptions.ProductException;
import com.pentax.pentazon.models.Cart;
import com.pentax.pentazon.models.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CartService {
    public Cart createCart();
    //Todo get user's cart
    public BigDecimal calculateCartTotal(String cartId) throws CartException;
    public void addItemToCart(String productId, int quantity,String cartId) throws CartException, ProductException;
    public void removeItemFromCart(String cartId, String productId) throws CartException;
    public CartDTO findCartById(String cartId) throws CartException;
    public void reduceCartItemQuantity(String cartId, String productId, int quantity) throws CartException, ProductException;
}
