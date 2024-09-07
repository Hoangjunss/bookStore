package com.nhom13.bookStore.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private  Integer id;
    private Integer idUser;
    private LocalDate createDay;
    private  Long totalPrice;
    private Integer totalQuantity;
    private  Integer idInventoryStatus;
}
