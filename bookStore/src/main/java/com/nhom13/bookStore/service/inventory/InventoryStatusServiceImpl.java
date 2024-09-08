package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.inventory.InventoryStatusDTO;
import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import com.nhom13.bookStore.model.inventory.InventoryStatus;
import com.nhom13.bookStore.repository.inventory.InventoryStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            log.info("Saving inventory status");
            InventoryStatus inventoryStatus = InventoryStatus.builder()
                    .id(inventoryStatusDTO.getId() == null ? generateId() : inventoryStatusDTO.getId())  // Generate ID if null
                    .name(inventoryStatusDTO.getName())
                    .build();
            return inventoryStatusRepository.save(inventoryStatus);
        } catch (DataIntegrityViolationException e) {
            log.error("Save inventory status failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while save inventory status: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public InventoryStatusDTO findById(Integer id) {
        log.info("Get inventory status by id: {}", id);
        InventoryStatus inventoryStatus = inventoryStatusRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.INVENTORY_STATUS_NOT_FOUND));
        return convertToDTO(inventoryStatus);
    }

    @Override
    public InventoryStatusDTO create(InventoryStatusDTO inventoryStatusDTO) {
        InventoryStatus savedInventoryStatus = save(inventoryStatusDTO);
        return convertToDTO(savedInventoryStatus);
    }

    @Override
    public InventoryStatusDTO update(InventoryStatusDTO inventoryStatusDTO) {
        try {
            log.info("Updating inventory status by id: {}", inventoryStatusDTO.getId());
            // Ensure the inventory status exists before updating
            findById(inventoryStatusDTO.getId());
            InventoryStatus updatedInventoryStatus = save(inventoryStatusDTO);
            return convertToDTO(updatedInventoryStatus);
        }  catch (DataIntegrityViolationException e) {
            log.error("Update inventory status failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while update inventory status: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            log.info("Deleting inventory status by id: {}", id);
            InventoryStatusDTO inventoryStatus = findById(id);
            inventoryStatusRepository.deleteById(inventoryStatus.getId());
        } catch (DataIntegrityViolationException e) {
            log.error("Delete inventory status failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while delete inventory status: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }
}
