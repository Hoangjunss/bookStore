package com.nhom13.bookStore.service.product;

import com.nhom13.bookStore.dto.product.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    CategoryDTO findById(Integer id);
    CategoryDTO create(CategoryDTO categoryDTO);
    CategoryDTO update(CategoryDTO categoryDTO);
    void delete(Integer id);
}
