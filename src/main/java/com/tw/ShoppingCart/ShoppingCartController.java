package com.tw.ShoppingCart;

import com.tw.ShoppingCart.Exception.ProductAlreadyPresentException;
import com.tw.ShoppingCart.Exception.ProductNotFoundException;
import com.tw.ShoppingCart.Exception.ProductNotPresentException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import static org.springframework.http.HttpStatus.OK;
@EnableWebMvc
@Controller
@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService productService;

    @GetMapping("/product")
    public Cart getProduct() {

        return productService.ViewCart();
    }

    @PostMapping("/product")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Product addProduct(@RequestBody ProductDTO productDTO) throws ProductAlreadyPresentException, ProductNotFoundException {

        return productService.addProduct(productDTO);
    }
    @DeleteMapping("/product/{id}")
    @ResponseBody
    ResponseEntity deleteProduct(@PathVariable int id) throws ProductNotFoundException {
        productService.removeProduct(id);
        return new ResponseEntity(OK);
    }
    @GetMapping("/product/total-bill")
    double totalBill(){
        return productService.ViewCart().getTotalBill();
    }

}
