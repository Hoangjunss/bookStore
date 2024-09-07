package com.nhom13.bookStore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {
    private Integer id;
    private Long priceProduct;
    private Long totalPrice;
    private Integer idProduct;
    private  Integer quantity;
    private  Integer idOrder;
}
