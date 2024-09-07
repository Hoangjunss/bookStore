package com.nhom13.bookStore.service.cart;

import org.springframework.stereotype.Service;

import com.nhom13.bookStore.dto.cart.CartDTO;

@Service
public interface CartService {
    CartDTO findById(Integer id);
    CartDTO findByIdCustomer(Integer id);
    CartDTO create(CartDTO cartDTO);
    CartDTO update(CartDTO cartDTO);
    void delete(Integer id);
}
