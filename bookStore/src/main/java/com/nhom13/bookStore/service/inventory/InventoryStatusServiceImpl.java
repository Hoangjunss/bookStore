package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.inventory.InventoryStatusDTO;
import com.nhom13.bookStore.model.inventory.InventoryStatus;
import com.nhom13.bookStore.repository.inventory.InventoryStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class InventoryStatusServiceImpl implements InventoryStatusService{
    @Autowired
    private InventoryStatusRepository inventoryStatusRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Convert InventoryStatus entity to InventoryStatusDTO
    private InventoryStatusDTO convertToDTO(InventoryStatus inventoryStatus) {
        return modelMapper.map(inventoryStatus, InventoryStatusDTO.class);
    }

    // Convert InventoryStatusDTO to InventoryStatus entity
    private InventoryStatus convertToModel(InventoryStatusDTO inventoryStatusDTO) {
        return modelMapper.map(inventoryStatusDTO, InventoryStatus.class);
    }

    // Generate a unique ID for new inventory status
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update InventoryStatus entity
    private InventoryStatus save(InventoryStatusDTO inventoryStatusDTO) {
        InventoryStatus inventoryStatus = InventoryStatus.builder()
                .id(inventoryStatusDTO.getId() == null ? generateId() : inventoryStatusDTO.getId())  // Generate ID if null
                .name(inventoryStatusDTO.getName())
                .build();
        return inventoryStatusRepository.save(inventoryStatus);
    }

    @Override
    public InventoryStatusDTO findById(Integer id) {
        InventoryStatus inventoryStatus = inventoryStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory status not found"));
        return convertToDTO(inventoryStatus);
    }

    @Override
    public InventoryStatusDTO create(InventoryStatusDTO inventoryStatusDTO) {
        InventoryStatus savedInventoryStatus = save(inventoryStatusDTO);
        return convertToDTO(savedInventoryStatus);
    }

    @Override
    public InventoryStatusDTO update(InventoryStatusDTO inventoryStatusDTO) {
        // Ensure the inventory status exists before updating
        inventoryStatusRepository.findById(inventoryStatusDTO.getId())
                .orElseThrow(() -> new RuntimeException("Inventory status not found"));

        InventoryStatus updatedInventoryStatus = save(inventoryStatusDTO);
        return convertToDTO(updatedInventoryStatus);
    }

    @Override
    public void delete(Integer id) {
        InventoryStatus inventoryStatus = inventoryStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory status not found"));
        inventoryStatusRepository.delete(inventoryStatus);
    }
}
