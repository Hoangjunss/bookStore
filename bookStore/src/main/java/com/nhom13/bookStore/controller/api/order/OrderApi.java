package com.nhom13.bookStore.controller.api.order;

import com.nhom13.bookStore.dto.ApiResponse;
import com.nhom13.bookStore.dto.order.OrderDetailsDTO;
import com.nhom13.bookStore.dto.order.OrdersDTO;
import com.nhom13.bookStore.service.order.OrderDetailService;
import com.nhom13.bookStore.service.order.OrdersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderApi {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDetailsDTO>> createOrderDetail(@ModelAttribute @Valid OrderDetailsDTO orderDetailDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Create order successful", orderDetailService.create(orderDetailDTO)));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<OrderDetailsDTO>> updateOrder(@ModelAttribute @Valid OrderDetailsDTO orderDetailDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Update order successful", orderDetailService.update(orderDetailDTO)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDetailsDTO>>> getAllOrders() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Get all orders successful", orderDetailService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDetailsDTO>> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Get order successful", orderDetailService.findById(id)));
    }
}