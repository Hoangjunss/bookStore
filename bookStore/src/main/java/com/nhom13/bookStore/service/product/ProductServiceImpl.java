package com.nhom13.bookStore.service.product;

import com.nhom13.bookStore.dto.product.ProductDTO;
import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import com.nhom13.bookStore.model.product.Product;
import com.nhom13.bookStore.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Convert Product entity to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    // Convert ProductDTO to Product entity
    private Product convertToModel(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
    public List<ProductDTO> convertToDtoList(List<Product> productList) {
        return productList.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    // Generate a unique ID for new products
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update Product entity
    private Product save(ProductDTO productDTO) {
        try {
            Product product = Product.builder()
                    .id(productDTO.getId() == null ? generateId() : productDTO.getId()) // Generate ID if null
                    .title(productDTO.getTitle())
                    .author(productDTO.getAuthor())
                    .publication_date(productDTO.getPublication_date())
                    .language(productDTO.getLanguage())
                    .page_count(productDTO.getPage_count())
                    .price(productDTO.getPrice())
                    .price_sale(productDTO.getPrice_sale())
                    .quantity(productDTO.getQuantity())
                    .idCategory(productDTO.getIdCategory())
                    .build();
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            log.error("Unable to save product: {}", e.getMessage());
            throw new CustomException(Error.PRODUCT_UNABLE_TO_SAVE);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while saving product: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public ProductDTO findById(Integer id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new CustomException(Error.PRODUCT_NOT_FOUND));
            return convertToDTO(product);
        } catch (CustomException e) {
            log.error("Error finding product by id {}: {}", id, e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        try {
            Product savedProduct = save(productDTO);
            return convertToDTO(savedProduct);
        } catch (CustomException e) {
            log.error("Error creating product: {}", e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        try {
            // Ensure the product exists before updating
            productRepository.findById(productDTO.getId())
                    .orElseThrow(() -> new CustomException(Error.PRODUCT_NOT_FOUND));

            Product updatedProduct = save(productDTO);
            return convertToDTO(updatedProduct);
        } catch (CustomException e) {
            log.error("Error updating product: {}", e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new CustomException(Error.PRODUCT_NOT_FOUND));
            productRepository.delete(product);
        } catch (CustomException e) {
            log.error("Error deleting product by id {}: {}", id, e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public List<ProductDTO> getAll() {
        try {
            List<Product> productList = productRepository.findAll();
            return convertToDtoList(productList);
        } catch (DataAccessException e) {
            log.error("Error retrieving product list: {}", e.getMessage());
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }
}
