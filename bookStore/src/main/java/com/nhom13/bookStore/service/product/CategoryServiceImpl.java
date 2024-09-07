package com.nhom13.bookStore.service.product;

import com.nhom13.bookStore.dto.product.CategoryDTO;
import com.nhom13.bookStore.model.product.Category;
import com.nhom13.bookStore.repository.product.CategoryReposytory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryReposytory categoryReposytory;
    @Autowired
    private ModelMapper modelMapper;

    // Convert Category entity to CategoryDTO
    private CategoryDTO convertToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    // Convert CategoryDTO to Category entity
    private Category convertToModel(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    // Generate a unique ID for new categories
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update Category entity
    private Category save(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .id(categoryDTO.getId() == null ? generateId() : categoryDTO.getId()) // Generate ID if null
                .name(categoryDTO.getName())
                .build();
        return categoryReposytory.save(category);
    }

    @Override
    public CategoryDTO findById(Integer id) {
        Category category = categoryReposytory.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return convertToDTO(category);
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category savedCategory = save(categoryDTO);
        return convertToDTO(savedCategory);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        // Ensure the category exists before updating
        categoryReposytory.findById(categoryDTO.getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Category updatedCategory = save(categoryDTO);
        return convertToDTO(updatedCategory);
    }

    @Override
    public void delete(Integer id) {
        Category category = categoryReposytory.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryReposytory.delete(category);
    }
}
