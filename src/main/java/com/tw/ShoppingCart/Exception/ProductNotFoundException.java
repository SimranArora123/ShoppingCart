package com.tw.ShoppingCart.Exception;

public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException(String product_not_found) {
        super(product_not_found);
    }
}
