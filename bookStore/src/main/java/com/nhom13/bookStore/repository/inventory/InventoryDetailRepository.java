package com.nhom13.bookStore.repository.inventory;

import com.nhom13.bookStore.model.account.Role;
import com.nhom13.bookStore.model.inventory.InventoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryDetailRepository extends JpaRepository<InventoryDetails,Integer> {
}
