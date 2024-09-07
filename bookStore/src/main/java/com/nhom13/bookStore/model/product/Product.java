package com.nhom13.bookStore.model.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private Integer id;
    private  String title;
    private String author;
    private LocalDate publication_date;
    private String language;
    private Integer page_count;
    private Long price;
    private  Long price_sale;
    private  Integer quantity;
}
