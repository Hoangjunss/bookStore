package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.OrderStatusDTO;
import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import com.nhom13.bookStore.model.order.OrderStatus;
import com.nhom13.bookStore.repository.order.OrderStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            OrderStatus orderStatus = OrderStatus.builder()
                    .id(orderStatusDTO.getId() == null ? generateId() : orderStatusDTO.getId()) // Generate ID if null
                    .name(orderStatusDTO.getName())
                    .build();
            return orderStatusRepository.save(orderStatus);
        } catch (DataIntegrityViolationException e) {
            log.error("Unable to save order status: {}", e.getMessage());
            throw new CustomException(Error.ORDER_STATUS_UNABLE_TO_SAVE);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while saving order status: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public OrderStatusDTO findById(Integer id) {
        try {
            OrderStatus orderStatus = orderStatusRepository.findById(id)
                    .orElseThrow(() -> new CustomException(Error.ORDER_STATUS_NOT_FOUND));
            return convertToDTO(orderStatus);
        } catch (CustomException e) {
            log.error("Error finding order status by id {}: {}", id, e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public OrderStatusDTO create(OrderStatusDTO orderStatusDTO) {
        try {
            OrderStatus savedOrderStatus = save(orderStatusDTO);
            return convertToDTO(savedOrderStatus);
        } catch (CustomException e) {
            log.error("Error creating order status: {}", e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public OrderStatusDTO update(OrderStatusDTO orderStatusDTO) {
        try {
            // Ensure the order status exists before updating
            orderStatusRepository.findById(orderStatusDTO.getId())
                    .orElseThrow(() -> new CustomException(Error.ORDER_STATUS_NOT_FOUND));

            OrderStatus updatedOrderStatus = save(orderStatusDTO);
            return convertToDTO(updatedOrderStatus);
        } catch (CustomException e) {
            log.error("Error updating order status: {}", e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            OrderStatus orderStatus = orderStatusRepository.findById(id)
                    .orElseThrow(() -> new CustomException(Error.ORDER_STATUS_NOT_FOUND));
            orderStatusRepository.delete(orderStatus);
        } catch (CustomException e) {
            log.error("Error deleting order status by id {}: {}", id, e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

}
