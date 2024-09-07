package com.nhom13.bookStore.model.cart;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {
   @Id
   private Integer id;
   private Integer idUser;
   private Integer totalQuantity;
   private Long totalPrice;
}
