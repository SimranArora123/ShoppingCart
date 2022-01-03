package com.tw.ShoppingCart;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ProductRepo extends CrudRepository<Product,Integer>{
    boolean existsByName(String name);
}
