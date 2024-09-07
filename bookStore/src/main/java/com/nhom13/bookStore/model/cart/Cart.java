package com.nhom13.bookStore.model.cart;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cart {
   @Id
   private Integer id;
   private Integer idUser;
   private Integer totalQuantity;
   private Long totalPrice;
}
