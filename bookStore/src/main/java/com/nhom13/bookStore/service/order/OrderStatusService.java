package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.OrderStatusDTO;
import org.springframework.stereotype.Service;

@Service
public interface OrderStatusService {
    OrderStatusDTO findById(Integer id);
    OrderStatusDTO create(OrderStatusDTO orderStatusDTO);
    OrderStatusDTO update(OrderStatusDTO orderStatusDTO);
    void delete(Integer id);
}
