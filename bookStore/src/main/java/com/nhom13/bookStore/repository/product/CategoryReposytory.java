package com.nhom13.bookStore.repository.product;

import com.nhom13.bookStore.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryReposytory extends JpaRepository<Category,Integer> {
}
