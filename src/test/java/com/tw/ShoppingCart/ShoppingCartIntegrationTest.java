package com.tw.ShoppingCart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class ShoppingCartIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ShoppingCartService productService;
    Product productOne;
    ProductDTO pencil;
    ProductDTO box;
    @Autowired
    private ProductRepo productRepository;

    @BeforeEach
    void setUp() {
        pencil = new ProductDTO();
        pencil.setName("Pencil");
        pencil.setPrice(100);
        productOne = new Product(pencil.getName(), pencil.getPrice());
        box = new ProductDTO();
        box.setName("Box");
        box.setPrice(200);
    }

    @Test
    void shouldBeAbleToAddProduct() throws Exception {
        this.mvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pencil)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeAbleToReturnEmptyList() throws Exception {
        Cart cart = new Cart(emptyList(), 0);
        this.mvc.perform(get("/product")).andExpect(status().isOk())
                .andExpect(content().json("{}"));
        assertTrue(((List<Product>) productRepository.findAll()).isEmpty());
    }

    @Test
    void shouldBeAbleToDeleteProductFromTheCart() throws Exception {
        productRepository.save(productOne);
        this.mvc.perform(delete("/product/{id}", productOne.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAbleToReturnTotalPriceOfTheCart() throws Exception {
        Cart cart = new Cart(asList(productOne), 100);
        String requestJson = mapper.writeValueAsString(box);
        productRepository.save(productOne);
        this.mvc.perform(get("/product")).andExpect(content().json(mapper.writeValueAsString(cart)))
                .andExpect(status().isOk());
        if (productRepository.existsById(productOne.getId()))
            productRepository.deleteById(productOne.getId());
    }


}
