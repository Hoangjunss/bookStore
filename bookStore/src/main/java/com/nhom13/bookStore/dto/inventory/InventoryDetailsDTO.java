package com.nhom13.bookStore.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDetailsDTO {
    private Integer id;
    private Long priceProduct;
    private Long totalPrice;
    private Integer idProduct;
    private  Integer quantity;
    private Integer idInventory;
}
