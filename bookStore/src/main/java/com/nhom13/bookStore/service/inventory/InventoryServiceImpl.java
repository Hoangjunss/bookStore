package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.inventory.InventoryDTO;
import com.nhom13.bookStore.model.inventory.Inventory;
import com.nhom13.bookStore.repository.inventory.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Convert Inventory entity to InventoryDTO
    private InventoryDTO convertToDTO(Inventory inventory) {
        return modelMapper.map(inventory, InventoryDTO.class);
    }

    // Convert InventoryDTO to Inventory entity
    private Inventory convertToModel(InventoryDTO inventoryDTO) {
        return modelMapper.map(inventoryDTO, Inventory.class);
    }

    // Generate a unique ID for new inventory
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update Inventory entity
    private Inventory save(InventoryDTO inventoryDTO) {
        Inventory inventory = Inventory.builder()
                .id(inventoryDTO.getId() == null ? generateId() : inventoryDTO.getId())  // Generate ID if null
                .idUser(inventoryDTO.getIdUser())
                .createDay(inventoryDTO.getCreateDay())
                .totalPrice(inventoryDTO.getTotalPrice())
                .totalQuantity(inventoryDTO.getTotalQuantity())
                .idInventoryStatus(inventoryDTO.getIdInventoryStatus())
                .build();
        return inventoryRepository.save(inventory);
    }

    @Override
    public InventoryDTO findById(Integer id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        return convertToDTO(inventory);
    }

    @Override
    public InventoryDTO create(InventoryDTO inventoryDTO) {
        // Create a new inventory
        Inventory savedInventory = save(inventoryDTO);
        return convertToDTO(savedInventory);
    }

    @Override
    public InventoryDTO update(InventoryDTO inventoryDTO) {
        // Ensure the inventory exists before updating
        Inventory existingInventory = inventoryRepository.findById(inventoryDTO.getId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        // Reuse save method to update the entity
        Inventory updatedInventory = save(inventoryDTO);
        return convertToDTO(updatedInventory);
    }

    @Override
    public void delete(Integer id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventoryRepository.delete(inventory);
    }
}
