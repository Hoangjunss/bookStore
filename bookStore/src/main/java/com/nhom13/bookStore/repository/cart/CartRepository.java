package com.nhom13.bookStore.repository.cart;

import com.nhom13.bookStore.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
}
