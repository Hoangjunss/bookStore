package com.nhom13.bookStore.service.product;

import com.nhom13.bookStore.dto.product.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    ProductDTO findById(Integer id);
    ProductDTO create(ProductDTO productDTO);
    ProductDTO update(ProductDTO productDTO);
    void delete(Integer id);
}
