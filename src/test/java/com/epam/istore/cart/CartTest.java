package com.epam.istore.cart;

import com.epam.istore.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CartTest {
    private Cart cart = new Cart();
    private Product product = new Product();

    @Test
    public void testCartOnAddingNewElement() {
        cart.addToCart(product, 10);
        assertEquals(1, cart.getAllProducts().size());
    }

    @Test
    public void testCartOnIncrementProductCountIfProductExists() {
        cart.addToCart(product, 10);
        cart.addToCart(product, 11);
        assertEquals(21, cart.getAllProducts().get(0).getCountOfProduct());
    }
}