package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.OrderDetailsDTO;
import com.nhom13.bookStore.model.order.OrderDetail;
import com.nhom13.bookStore.repository.order.OrderDetailReposiroty;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    private OrderDetailReposiroty orderDetailReposiroty;
    @Autowired
    private ModelMapper modelMapper;

    // Convert OrderDetail entity to OrderDetailsDTO
    private OrderDetailsDTO convertToDTO(OrderDetail orderDetail) {
        return modelMapper.map(orderDetail, OrderDetailsDTO.class);
    }

    // Convert OrderDetailsDTO to OrderDetail entity
    private OrderDetail convertToModel(OrderDetailsDTO orderDetailsDTO) {
        return modelMapper.map(orderDetailsDTO, OrderDetail.class);
    }

    // Generate a unique ID for new order detail
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update OrderDetail entity
    private OrderDetail save(OrderDetailsDTO orderDetailsDTO) {
        OrderDetail orderDetail = OrderDetail.builder()
                .id(orderDetailsDTO.getId() == null ? generateId() : orderDetailsDTO.getId())  // Generate ID if null
                .priceProduct(orderDetailsDTO.getPriceProduct())
                .totalPrice(orderDetailsDTO.getTotalPrice())
                .idProduct(orderDetailsDTO.getIdProduct())
                .quantity(orderDetailsDTO.getQuantity())
                .idOrder(orderDetailsDTO.getIdOrder())
                .build();
        return orderDetailReposiroty.save(orderDetail);
    }

    @Override
    public OrderDetailsDTO findById(Integer id) {
        OrderDetail orderDetail = orderDetailReposiroty.findById(id)
                .orElseThrow(() -> new RuntimeException("Order detail not found"));
        return convertToDTO(orderDetail);
    }

    @Override
    public OrderDetailsDTO create(OrderDetailsDTO orderDetailsDTO) {
        OrderDetail savedOrderDetail = save(orderDetailsDTO);
        return convertToDTO(savedOrderDetail);
    }

    @Override
    public OrderDetailsDTO update(OrderDetailsDTO orderDetailsDTO) {
        // Ensure the order detail exists before updating
        orderDetailReposiroty.findById(orderDetailsDTO.getId())
                .orElseThrow(() -> new RuntimeException("Order detail not found"));

        OrderDetail updatedOrderDetail = save(orderDetailsDTO);
        return convertToDTO(updatedOrderDetail);
    }

    @Override
    public void delete(Integer id) {
        OrderDetail orderDetail = orderDetailReposiroty.findById(id)
                .orElseThrow(() -> new RuntimeException("Order detail not found"));
        orderDetailReposiroty.delete(orderDetail);
    }
}
