package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.inventory.InventoryDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryDetailService {
    InventoryDetailsDTO findById(Integer id);
    InventoryDetailsDTO create(InventoryDetailsDTO inventoryDetailsDTO);
    InventoryDetailsDTO update(InventoryDetailsDTO inventoryDetailsDTO);
    void delete(Integer id);
    List<InventoryDetailsDTO> getByIdInventory(Integer id);
}
