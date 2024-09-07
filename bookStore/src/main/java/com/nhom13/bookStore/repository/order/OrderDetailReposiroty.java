package com.nhom13.bookStore.repository.order;

import com.nhom13.bookStore.model.account.Role;
import com.nhom13.bookStore.model.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailReposiroty extends JpaRepository<OrderDetail,Integer> {
}
