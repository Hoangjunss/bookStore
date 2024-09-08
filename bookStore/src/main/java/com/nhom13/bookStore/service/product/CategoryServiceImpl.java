package com.nhom13.bookStore.service.product;

import com.nhom13.bookStore.dto.product.CategoryDTO;
import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import com.nhom13.bookStore.model.product.Category;
import com.nhom13.bookStore.repository.product.CategoryReposytory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryReposytory categoryRepository;
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
        try {
            Category category = Category.builder()
                    .id(categoryDTO.getId() == null ? generateId() : categoryDTO.getId()) // Generate ID if null
                    .name(categoryDTO.getName())
                    .build();
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            log.error("Unable to save category: {}", e.getMessage());
            throw new CustomException(Error.CATEGORY_UNABLE_TO_SAVE);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while saving category: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public CategoryDTO findById(Integer id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CustomException(Error.CATEGORY_NOT_FOUND));
            return convertToDTO(category);
        } catch (CustomException e) {
            log.error("Error finding category by id {}: {}", id, e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        try {
            Category savedCategory = save(categoryDTO);
            return convertToDTO(savedCategory);
        } catch (CustomException e) {
            log.error("Error creating category: {}", e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        try {
            // Ensure the category exists before updating
            categoryRepository.findById(categoryDTO.getId())
                    .orElseThrow(() -> new CustomException(Error.CATEGORY_NOT_FOUND));

            Category updatedCategory = save(categoryDTO);
            return convertToDTO(updatedCategory);
        } catch (CustomException e) {
            log.error("Error updating category: {}", e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CustomException(Error.CATEGORY_NOT_FOUND));
            categoryRepository.delete(category);
        } catch (CustomException e) {
            log.error("Error deleting category by id {}: {}", id, e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }
}
