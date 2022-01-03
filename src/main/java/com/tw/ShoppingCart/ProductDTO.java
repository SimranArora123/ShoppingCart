package com.tw.ShoppingCart;

import java.util.Objects;

public class ProductDTO {
    private String name;
    private float price;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this.getClass() != obj.getClass())
            return false;
        ProductDTO product = (ProductDTO) obj;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    public ProductDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


}
