package com.tw.ShoppingCart;

import com.tw.ShoppingCart.Exception.ProductAlreadyPresentException;
import com.tw.ShoppingCart.Exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private ProductRepo productRepository;

    public Product addProduct(ProductDTO productDTO) throws ProductAlreadyPresentException, ProductNotFoundException {

        Product product = new Product(productDTO.getName(), productDTO.getPrice());
        if (product.getName().equals("")) {
            throw new ProductNotFoundException("Please Enter Product Name");
        }
        if (productRepository.existsByName(product.getName())) {
            throw new ProductAlreadyPresentException("Product Already Present");
        }
        Product prod = productRepository.save(product);
        return prod;
    }

    public boolean removeProduct(int id) {
        Optional<Product> pro = productRepository.findById(id);
        if (pro.isPresent())
            productRepository.deleteById(id);
        return true;
    }


    public Cart ViewCart() {
        double totalAmount = 0;
        List<Product> shoppingList = (List<Product>) productRepository.findAll();
        for (Product item : shoppingList) {
            totalAmount += item.getPrice();
        }
        return new Cart(shoppingList, totalAmount);
    }

    public Product findById(int id) throws ProductNotFoundException {
        if (!productRepository.existsById(id))
            throw new ProductNotFoundException("Product Not Found");
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }

}
