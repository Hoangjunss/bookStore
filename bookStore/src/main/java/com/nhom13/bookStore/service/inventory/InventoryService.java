package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.inventory.InventoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {
    InventoryDTO findById(Integer id);
    InventoryDTO create(InventoryDTO inventoryDTO);
    InventoryDTO update(InventoryDTO inventoryDTO);
    void delete(Integer id);

}
