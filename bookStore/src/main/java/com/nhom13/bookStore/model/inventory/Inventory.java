package com.nhom13.bookStore.model.inventory;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Inventory {
    @Id
    private  Integer id;
    private LocalDate createDay;
    private  Long totalPrice;
    private Integer totalQuantity;
    private  Integer idInventoryStatus;

}
