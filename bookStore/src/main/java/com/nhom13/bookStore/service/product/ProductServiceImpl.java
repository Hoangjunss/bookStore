package com.nhom13.bookStore.service.product;

import com.nhom13.bookStore.dto.product.ProductDTO;
import com.nhom13.bookStore.model.product.Product;
import com.nhom13.bookStore.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    // Generate a unique ID for new products
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update Product entity
    private Product save(ProductDTO productDTO) {
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
    }

    @Override
    public ProductDTO findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Product savedProduct = save(productDTO);
        return convertToDTO(savedProduct);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        // Ensure the product exists before updating
        productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Product updatedProduct = save(productDTO);
        return convertToDTO(updatedProduct);
    }

    @Override
    public void delete(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}
