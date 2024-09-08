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
    public ResponseEntity<ApiResponse<OrdersDTO>> createOrder(@RequestParam Integer idCart) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Create order successful", ordersService.create(idCart)));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<OrdersDTO>> updateOrder(@ModelAttribute @Valid OrdersDTO orderDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Update order successful", ordersService.update(orderDTO)));
    }



    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrdersDTO>>> getOrderByIdUser(@RequestParam Integer idUser) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Get order successful", ordersService.findByIdUser(idUser)));
    }
}