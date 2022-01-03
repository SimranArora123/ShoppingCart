package com.tw.ShoppingCart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.ShoppingCart.Exception.ProductAlreadyPresentException;
import com.tw.ShoppingCart.Exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ShoppingCartService productService;
    private ProductDTO productDTO;
    @BeforeEach
    void setUp(){
        productDTO=new ProductDTO();
        productDTO.setName("Apple");
        productDTO.setPrice(200);
    }
    @Test
    void shouldBeAbleToViewCart() throws Exception {
        Product product=new Product();
        product.setName("Mango");
        product.setPrice(100);
        Product productOne=new Product(productDTO.getName(),productDTO.getPrice());
        double totalBill=300;

        Cart cart=new Cart(Arrays.asList(productOne,product),totalBill);
        when(productService.ViewCart()).thenReturn(cart);
        this.mvc.perform(get("/product")).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(cart)));
        verify(productService).ViewCart();
    }
    @Test
    void shouldBeAbleToAddProduct() throws Exception, ProductAlreadyPresentException, ProductNotFoundException {
        Product productOne=new Product(productDTO.getName(),productDTO.getPrice());
        when(productService.addProduct(productDTO)).thenReturn(productOne);
        String requestJson = mapper.writeValueAsString(productDTO);

        this.mvc.perform(post("/product")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(productDTO)));

        verify(productService).addProduct(productDTO);
    }
    @Test
    void shouldBeAbleToDeleteProduct() throws ProductNotFoundException, Exception {
        Product productOne=new Product(productDTO.getName(),productDTO.getPrice());
        when(productService.removeProduct(productOne.getId())).thenReturn(Boolean.TRUE);
        this.mvc.perform(delete("/product/{id}",productOne.getId())).andExpect(status().isOk());
        verify(productService).removeProduct(productOne.getId());
       }


}

