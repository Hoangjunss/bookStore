package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.inventory.InventoryDTO;
import com.nhom13.bookStore.dto.order.OrdersDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    InventoryDTO findById(Integer id);
    InventoryDTO create(Integer idOrder);
    InventoryDTO update(InventoryDTO inventoryDTO);
    void delete(Integer id);
    List<InventoryDTO> findByIdUser(Integer id);

}
