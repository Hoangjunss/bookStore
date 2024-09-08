package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.OrdersDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrdersService {
    OrdersDTO findById(Integer id);
    List<OrdersDTO> findByIdUser(Integer idUser);
    OrdersDTO create(Integer idCart);
    OrdersDTO update(OrdersDTO ordersDTO);
    void delete(Integer id);
    List<OrdersDTO> getAll();
}
