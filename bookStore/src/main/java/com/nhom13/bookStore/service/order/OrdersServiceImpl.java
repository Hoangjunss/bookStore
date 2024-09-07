package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.OrdersDTO;
import com.nhom13.bookStore.model.order.Orders;
import com.nhom13.bookStore.repository.order.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrdersServiceImpl implements OrdersService{
    @Autowired
    private OrderRepository ordersRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Convert Orders entity to OrdersDTO
    private OrdersDTO convertToDTO(Orders orders) {
        return modelMapper.map(orders, OrdersDTO.class);
    }

    // Convert OrdersDTO to Orders entity
    private Orders convertToModel(OrdersDTO ordersDTO) {
        return modelMapper.map(ordersDTO, Orders.class);
    }

    // Generate a unique ID for new orders
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update Orders entity
    private Orders save(OrdersDTO ordersDTO) {
        Orders orders = Orders.builder()
                .id(ordersDTO.getId() == null ? generateId() : ordersDTO.getId())  // Generate ID if null
                .idUser(ordersDTO.getIdUser())
                .createDay(ordersDTO.getCreateDay())
                .totalPrice(ordersDTO.getTotalPrice())
                .totalQuantity(ordersDTO.getTotalQuantity())
                .paymentStatus(ordersDTO.getPaymentStatus())
                .idOrderStatus(ordersDTO.getIdOrderStatus())
                .build();
        return ordersRepository.save(orders);
    }

    @Override
    public OrdersDTO findById(Integer id) {
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(orders);
    }

    @Override
    public OrdersDTO create(OrdersDTO ordersDTO) {
        Orders savedOrders = save(ordersDTO);
        return convertToDTO(savedOrders);
    }

    @Override
    public OrdersDTO update(OrdersDTO ordersDTO) {
        // Ensure the order exists before updating
        ordersRepository.findById(ordersDTO.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Orders updatedOrders = save(ordersDTO);
        return convertToDTO(updatedOrders);
    }

    @Override
    public void delete(Integer id) {
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        ordersRepository.delete(orders);
    }
}
