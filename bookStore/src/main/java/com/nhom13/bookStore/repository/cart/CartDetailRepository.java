package com.nhom13.bookStore.repository.cart;


import com.nhom13.bookStore.model.cart.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetails,Integer> {
}
