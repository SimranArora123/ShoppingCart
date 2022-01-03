package com.tw.ShoppingCart;

import java.util.List;
import java.util.Objects;

public class Cart {
    private List<Product>cartList;
    private double totalPrice;
    public Cart(){

    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this.getClass() != obj.getClass())
            return false;
        Cart cart = (Cart) obj;
        return Objects.equals(cartList, cart.cartList) && Objects.equals(totalPrice, cart.totalPrice);

    }
    public Cart(List<Product> cartList, double totalPrice) {
      this.cartList=cartList;
      this.totalPrice=totalPrice;
    }
    public List<Product> getMyShoppingList() {
        return cartList;
    }

    public double getTotalBill() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice){
        this.totalPrice=totalPrice;
    }
}
