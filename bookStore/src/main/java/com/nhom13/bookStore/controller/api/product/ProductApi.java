package com.nhom13.bookStore.controller.api.product;

import com.nhom13.bookStore.dto.ApiResponse;
import com.nhom13.bookStore.dto.product.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductApi {
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createProduct(@ModelAttribute @Valid ProductDTO productDTO){

        return  ResponseEntity.ok(new ApiResponse<>());
    }
    @PatchMapping
    public ResponseEntity<ApiResponse<String>> updateProduct(){
        return  ResponseEntity.ok(new ApiResponse<>());
    }
    @GetMapping
    public ResponseEntity<ApiResponse<String>> getProduct(){
        return  ResponseEntity.ok(new ApiResponse<>());
    }



}
