package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.OrdersDTO;
import org.springframework.stereotype.Service;

@Service
public interface OrdersService {
    OrdersDTO findById(Integer id);
    OrdersDTO create(OrdersDTO ordersDTO);
    OrdersDTO update(OrdersDTO ordersDTO);
    void delete(Integer id);
}
