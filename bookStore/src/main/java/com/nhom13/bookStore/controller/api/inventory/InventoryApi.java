package com.nhom13.bookStore.controller.api.inventory;

import com.nhom13.bookStore.dto.ApiResponse;
import com.nhom13.bookStore.dto.inventory.InventoryDTO;
import com.nhom13.bookStore.dto.order.OrderDetailsDTO;
import com.nhom13.bookStore.dto.order.OrdersDTO;
import com.nhom13.bookStore.service.inventory.InventoryDetailService;
import com.nhom13.bookStore.service.inventory.InventoryService;
import com.nhom13.bookStore.service.order.OrderDetailService;
import com.nhom13.bookStore.service.order.OrdersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryApi {
    @Autowired
    private InventoryDetailService inventoryDetailService;
    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryDTO>> createInventory(@RequestParam Integer idOrder) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Create order successful", inventoryService.create(idOrder)));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<InventoryDTO>> updateInventory( @ModelAttribute @Valid InventoryDTO inventoryDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Update order successful", inventoryService.update(inventoryDTO)));
    }



    @GetMapping()
    public ResponseEntity<ApiResponse<List<InventoryDTO>>> getInventoryByIdUser(@RequestParam Integer idUser) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Get order successful", inventoryService.findByIdUser(idUser)));
    }
    @GetMapping("/inventorydetail")
    public ResponseEntity<ApiResponse<List<InventoryDTO>>> getInventoryDetailByIdOrder(@RequestParam Integer idInventory) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Get order successful", inventoryService.findByIdUser(idInventory)));
    }
}
