package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.inventory.InventoryStatusDTO;
import org.springframework.stereotype.Service;

@Service
public interface InventoryStatusService {
    InventoryStatusDTO findById(Integer id);
    InventoryStatusDTO create(InventoryStatusDTO inventoryStatusDTO);
    InventoryStatusDTO update(InventoryStatusDTO inventoryStatusDTO);
    void delete(Integer id);
}
