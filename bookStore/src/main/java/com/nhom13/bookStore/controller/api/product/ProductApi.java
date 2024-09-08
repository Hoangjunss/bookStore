package com.nhom13.bookStore.controller.api.product;

import com.nhom13.bookStore.dto.ApiResponse;
import com.nhom13.bookStore.dto.product.ProductDTO;
import com.nhom13.bookStore.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductApi {
    @Autowired
    private ProductService productService;
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@ModelAttribute @Valid ProductDTO productDTO){

        return  ResponseEntity.ok(new ApiResponse<>(true,"create product successful",productService.create(productDTO)));
    }
    @PatchMapping
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@ModelAttribute @Valid ProductDTO productDTO){
        return  ResponseEntity.ok(new ApiResponse<>(true,"update product successful",productService.update(productDTO)));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProduct(){
        return  ResponseEntity.ok(new ApiResponse<>(true,"get all product successful",productService.getAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@RequestParam Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Get product successful", productService.findById(id)));
    }


}
