package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.OrderStatusDTO;
import com.nhom13.bookStore.model.order.OrderStatus;
import com.nhom13.bookStore.repository.order.OrderStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderStatusServiceImpl implements OrderStatusService{
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Convert OrderStatus entity to OrderStatusDTO
    private OrderStatusDTO convertToDTO(OrderStatus orderStatus) {
        return modelMapper.map(orderStatus, OrderStatusDTO.class);
    }

    // Convert OrderStatusDTO to OrderStatus entity
    private OrderStatus convertToModel(OrderStatusDTO orderStatusDTO) {
        return modelMapper.map(orderStatusDTO, OrderStatus.class);
    }

    // Generate a unique ID for new order status
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update OrderStatus entity
    private OrderStatus save(OrderStatusDTO orderStatusDTO) {
        OrderStatus orderStatus = OrderStatus.builder()
                .id(orderStatusDTO.getId() == null ? generateId() : orderStatusDTO.getId()) // Generate ID if null
                .name(orderStatusDTO.getName())
                .build();
        return orderStatusRepository.save(orderStatus);
    }

    @Override
    public OrderStatusDTO findById(Integer id) {
        OrderStatus orderStatus = orderStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order status not found"));
        return convertToDTO(orderStatus);
    }

    @Override
    public OrderStatusDTO create(OrderStatusDTO orderStatusDTO) {
        OrderStatus savedOrderStatus = save(orderStatusDTO);
        return convertToDTO(savedOrderStatus);
    }

    @Override
    public OrderStatusDTO update(OrderStatusDTO orderStatusDTO) {
        // Ensure the order status exists before updating
        orderStatusRepository.findById(orderStatusDTO.getId())
                .orElseThrow(() -> new RuntimeException("Order status not found"));

        OrderStatus updatedOrderStatus = save(orderStatusDTO);
        return convertToDTO(updatedOrderStatus);
    }

    @Override
    public void delete(Integer id) {
        OrderStatus orderStatus = orderStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order status not found"));
        orderStatusRepository.delete(orderStatus);
    }
}
