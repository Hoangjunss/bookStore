package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.inventory.InventoryDTO;
import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import com.nhom13.bookStore.model.inventory.Inventory;
import com.nhom13.bookStore.repository.inventory.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            log.info("Saving inventory");
            Inventory inventory = Inventory.builder()
                    .id(inventoryDTO.getId() == null ? generateId() : inventoryDTO.getId())  // Generate ID if null
                    .idUser(inventoryDTO.getIdUser())
                    .createDay(inventoryDTO.getCreateDay())
                    .totalPrice(inventoryDTO.getTotalPrice())
                    .totalQuantity(inventoryDTO.getTotalQuantity())
                    .idInventoryStatus(inventoryDTO.getIdInventoryStatus())
                    .build();
            return inventoryRepository.save(inventory);
        }  catch (DataIntegrityViolationException e) {
            log.error("Save inventory failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while save inventory: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public InventoryDTO findById(Integer id) {
        log.info("Get inventory by id: {}", id);
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.INVENTORY_NOT_FOUND));
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
        try {
            log.info("Update inventory by id: {}", inventoryDTO.getId());
            // Ensure the inventory exists before updating
            InventoryDTO existingInventory = findById(inventoryDTO.getId());
            // Reuse save method to update the entity
            Inventory updatedInventory = save(inventoryDTO);
            return convertToDTO(updatedInventory);
        } catch (DataIntegrityViolationException e) {
            log.error("Update inventory failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while update inventory: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            log.info("Delete inventory by id: {}", id);
            InventoryDTO inventory = findById(id);
            inventoryRepository.deleteById(inventory.getId());
        }catch (DataIntegrityViolationException e) {
            log.error("Delete inventory failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while delete inventory: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }
}
