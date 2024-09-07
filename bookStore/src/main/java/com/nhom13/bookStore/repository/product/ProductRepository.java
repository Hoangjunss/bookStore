package com.nhom13.bookStore.repository.product;

import com.nhom13.bookStore.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
