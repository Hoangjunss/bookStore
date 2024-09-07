package com.nhom13.bookStore.dto.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer id;
    private  String title;
    private String author;
    private LocalDate publication_date;
    private String language;
    private Integer page_count;
    private Long price;
    private  Long price_sale;
    private  Integer quantity;
    private Integer idCategory;
}
