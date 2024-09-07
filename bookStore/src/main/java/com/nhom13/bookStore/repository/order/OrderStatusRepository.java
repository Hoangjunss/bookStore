package com.nhom13.bookStore.repository.order;

import com.nhom13.bookStore.model.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus,Integer> {
}
