package com.nhom13.bookStore.repository.inventory;

import com.nhom13.bookStore.model.account.Role;
import com.nhom13.bookStore.model.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
    List<Inventory> findByIdUser (Integer idUser);
}
