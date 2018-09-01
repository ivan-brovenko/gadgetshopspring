package com.epam.istore.cart;


import com.epam.istore.bean.ProductInCartBean;
import com.epam.istore.model.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {
    private Map<Product, Integer> products = new ConcurrentHashMap<>();
    private Integer totalCount;
    private Double totalPrice;

    public Cart() {
        totalCount = products.size();
        totalPrice = products.entrySet().stream().mapToDouble((entry) -> entry.getKey().getPrice() * entry.getValue()).sum();
    }

    public void addToCart(Product product, int count) {
        if (products.containsKey(product)) {
            int quantity = products.get(product) + count;
            products.put(product, quantity);
            return;
        }
        products.put(product, count);
    }

    public void removeFromCart(int productId) {
        products.entrySet().stream().filter((entry) -> entry.getKey().getId() == productId).forEach((e) -> products.remove(e.getKey()));
    }

    public void set(int productId, int count) {
        if (count > 0 && containsProductWithId(productId)) {
            products.put(products.keySet().stream().filter((product) -> (product).getId() == productId).findFirst().get(), count);
        }
    }

    private boolean containsProductWithId(int productId) {
        return products.keySet().stream().anyMatch(product -> product.getId() == productId);
    }

    public List<ProductInCartBean> getAllProducts() {
        List<ProductInCartBean> productList = new ArrayList<>();
        products.entrySet().forEach((entry) -> productList.add(new ProductInCartBean(entry.getKey(), entry.getValue())));
        return productList;
    }

    public double getTotalPrice() {
        return products.entrySet().stream().mapToDouble((entry) -> entry.getKey().getPrice() * entry.getValue()).sum();
    }

    public int getTotalCount() {
        return products.size();
    }

    public void clear() {
        products.clear();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + products.toString() +
                '}';
    }
}
