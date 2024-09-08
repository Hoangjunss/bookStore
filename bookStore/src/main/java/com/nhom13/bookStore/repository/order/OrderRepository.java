package com.nhom13.bookStore.repository.order;

import com.nhom13.bookStore.model.account.Role;
import com.nhom13.bookStore.model.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findByIdUser(Integer idUser);
}
