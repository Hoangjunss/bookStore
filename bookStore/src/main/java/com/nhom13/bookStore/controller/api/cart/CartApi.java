package com.nhom13.bookStore.controller.api.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nhom13.bookStore.dto.ApiResponse;
import com.nhom13.bookStore.dto.cart.CartDTO;
import com.nhom13.bookStore.dto.cart.CartDetailsDTO;
import com.nhom13.bookStore.service.cart.CartDetailsService;
import com.nhom13.bookStore.service.cart.CartService;


@RestController
@RequestMapping("/api/v1/cart")
public class CartApi {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDetailsService cartDetailsService;

    @PostMapping
    public ResponseEntity<ApiResponse<CartDetailsDTO>> createCartDetail(@RequestBody CartDetailsDTO cartDetailsDTO,@RequestParam("idUser") Integer idUser){
        return  ResponseEntity.ok(new ApiResponse<>(true, "Create cart detail successfully", cartDetailsService.create(cartDetailsDTO, idUser)));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<CartDetailsDTO>> updateCartDetail(@RequestBody CartDetailsDTO cartDetailsDTO){
        return  ResponseEntity.ok(new ApiResponse<>(true, "Update cart detail successfully", cartDetailsService.update(cartDetailsDTO)));
    }


    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteCartDetail(@PathVariable("id") Integer id){
        return  ResponseEntity.ok(new ApiResponse<>(true, "Delete cart detail successfully", "successfully"));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<CartDTO>> createCart(@RequestBody CartDTO cartDTO){
        return  ResponseEntity.ok(new ApiResponse<>(true, "Create cart successfully", cartService.create(cartDTO)));
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<ApiResponse<CartDTO>> getCartByUser(@PathVariable("idUser") Integer id){
        return  ResponseEntity.ok(new ApiResponse<>(true, "Get cart by user successfully", cartService.findByIdCustomer(id)));
    }



    
}
