package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.OrderDetailsDTO;
import com.nhom13.bookStore.dto.order.OrdersDTO;
import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import com.nhom13.bookStore.model.cart.CartDetails;
import com.nhom13.bookStore.model.order.OrderDetail;
import com.nhom13.bookStore.model.order.Orders;
import com.nhom13.bookStore.repository.order.OrderDetailReposiroty;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<OrderDetailsDTO> convertToDtoList(List<OrderDetail> ordersDetailsList) {
        return ordersDetailsList.stream()
                .map(product -> modelMapper.map(product, OrderDetailsDTO.class))
                .collect(Collectors.toList());
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
        try {
            log.info("Saving OrderDetail");
            OrderDetail orderDetail = OrderDetail.builder()
                    .id(orderDetailsDTO.getId() == null ? generateId() : orderDetailsDTO.getId())  // Generate ID if null
                    .priceProduct(orderDetailsDTO.getPriceProduct())
                    .totalPrice(orderDetailsDTO.getTotalPrice())
                    .idProduct(orderDetailsDTO.getIdProduct())
                    .quantity(orderDetailsDTO.getQuantity())
                    .idOrder(orderDetailsDTO.getIdOrder())
                    .build();
            return orderDetailReposiroty.save(orderDetail);
        } catch (DataIntegrityViolationException e) {
            log.error("Save order details failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while save order details: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public OrderDetailsDTO findById(Integer id) {
        log.info("Find order detail by id: {}", id);
        OrderDetail orderDetail = orderDetailReposiroty.findById(id)
                .orElseThrow(() -> new CustomException(Error.ORDER_DETAILS_NOT_FOUND));
        return convertToDTO(orderDetail);
    }

    @Override
    public OrderDetailsDTO create(OrderDetailsDTO orderDetailsDTO) {
        OrderDetail savedOrderDetail = save(orderDetailsDTO);
        return convertToDTO(savedOrderDetail);
    }

    @Override
    public OrderDetailsDTO update(OrderDetailsDTO orderDetailsDTO) {
        try {
            log.info("Updating order details with id: {}", orderDetailsDTO.getId());
            orderDetailReposiroty.findById(orderDetailsDTO.getId());
            OrderDetail updatedOrderDetail = save(orderDetailsDTO);
            return convertToDTO(updatedOrderDetail);
        } catch (DataIntegrityViolationException e) {
            log.error("Update order details failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while update order details: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            log.info("Deleting order details with id: {}", id);
            OrderDetailsDTO orderDetail = findById(id);
            orderDetailReposiroty.deleteById(orderDetail.getId());
        } catch (DataIntegrityViolationException e) {
            log.error("Delete order details failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while delete order details: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public List<OrderDetailsDTO> getAll(Integer id) {
        return convertToDtoList(orderDetailReposiroty.findByIdOrder(id));
    }
}
