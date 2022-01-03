package com.tw.ShoppingCart;

import com.tw.ShoppingCart.Exception.ProductAlreadyPresentException;
import com.tw.ShoppingCart.Exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

@MockitoSettings(strictness = Strictness.LENIENT)
public class ShoppingCartServiceTest {
    @Mock
    ProductRepo productRepository;
    @InjectMocks
    ShoppingCartService productService;
    private ProductDTO productDTO;
    private Product productOne;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setPrice(10);
        productDTO.setName("Bread");
        productOne = new Product();
        productOne.setName("Box");
        productOne.setPrice(30);

    }

    @Test
    void shouldBeAbleToAddProductInCart() throws ProductAlreadyPresentException, ProductNotFoundException {

        when(productRepository.save(any(Product.class))).thenReturn(productOne);
        Product expectedId = productService.addProduct(productDTO);
        assertThat(productOne, is(equalTo(expectedId)));
        verify(productRepository).save(any(Product.class));

    }


    /*@Test
    void shouldBeAbleToDeleteProduct() throws ProductNotFoundException {
        Product product=new Product(productDTO.getName(),productDTO.getPrice());
        when(productRepository.save(product)).thenReturn(product);
        productService.removeProduct(product.getId());
        verify(productRepository).deleteById(product.getId());
    }*/
    @Test
    void shouldBeAbleToViewCart() {
        Product product = new Product(productDTO.getName(), productDTO.getPrice());
        List<Product> expectedList = new ArrayList<>();
        expectedList.add(product);
        expectedList.add(productOne);
        float expectedBill = 40;
        Cart expectedCart = new Cart(expectedList, expectedBill);
        when(productRepository.findAll()).thenReturn(expectedList);
        assertThat(productService.ViewCart(), is(equalTo(expectedCart)));
        verify(productRepository).findAll();
    }

    @Test
    void shouldBeAbleToSearchProductById() throws ProductNotFoundException {
        Product product = new Product(productDTO.getName(), productDTO.getPrice());
        when(productRepository.existsById(product.getId())).thenReturn(true);
        when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(
                product
        ));
        Product actualProduct = productService.findById(product.getId());
        assertThat(actualProduct, is(equalTo(product)));
        verify(productRepository).existsById(product.getId());
        verify(productRepository).findById(product.getId());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNotProvided() {
        productDTO.setName("");
        assertThrows(ProductNotFoundException.class, () -> {
            productService.addProduct(productDTO);
        });
    }


}

