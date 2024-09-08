package com.nhom13.bookStore.service.cart;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom13.bookStore.dto.cart.CartDetailsDTO;

@Service
public interface CartDetailsService {
    CartDetailsDTO findById(Integer id);
    CartDetailsDTO create(CartDetailsDTO cartDetailsDTO,Integer idUser);
    CartDetailsDTO update(CartDetailsDTO cartDetailsDTO);
    void delete(Integer id);
    List<CartDetailsDTO> getCartDetailsByIdCart(Integer id);


}
