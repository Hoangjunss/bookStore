package com.nhom13.bookStore.repository.inventory;

import com.nhom13.bookStore.model.account.Role;
import com.nhom13.bookStore.model.inventory.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryStatusRepository extends JpaRepository<InventoryStatus,Integer> {
}
