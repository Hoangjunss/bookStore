package com.nhom13.bookStore.model.inventory;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Inventory {
    @Id
    private  Integer id;
    private Integer idUser;
    private LocalDate createDay;
    private  Long totalPrice;
    private Integer totalQuantity;
    private  Integer idInventoryStatus;

}
