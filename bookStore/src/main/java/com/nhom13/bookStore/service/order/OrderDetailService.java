package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.OrderDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    OrderDetailsDTO findById(Integer id);
    OrderDetailsDTO create(OrderDetailsDTO orderDetailsDTO);
    OrderDetailsDTO update(OrderDetailsDTO orderDetailsDTO);
    void delete(Integer id);
    List<OrderDetailsDTO> getAll(Integer id);
}
