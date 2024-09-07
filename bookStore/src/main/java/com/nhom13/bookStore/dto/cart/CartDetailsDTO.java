package com.nhom13.bookStore.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsDTO {
    private Integer id;
    private Long priceProduct;
    private Long totalPrice;
    private Integer idProduct;
    private  Integer quantity;
    private  Integer idCart;
}
