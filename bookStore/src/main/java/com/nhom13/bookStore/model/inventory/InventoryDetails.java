package com.nhom13.bookStore.model.inventory;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InventoryDetails {
    @Id
    private Integer id;
    private Long priceProduct;
    private Long totalPrice;
    private Integer idProduct;
    private  Integer quantity;
    private Integer idInventory;
}
