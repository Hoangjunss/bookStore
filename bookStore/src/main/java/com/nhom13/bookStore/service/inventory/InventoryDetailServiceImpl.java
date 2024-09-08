package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.inventory.InventoryDetailsDTO;
import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import com.nhom13.bookStore.model.inventory.InventoryDetails;
import com.nhom13.bookStore.repository.inventory.InventoryDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryDetailServiceImpl implements  InventoryDetailService{
    @Autowired
    private InventoryDetailRepository inventoryDetailRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Convert InventoryDetails entity to InventoryDetailsDTO
    private InventoryDetailsDTO convertToDTO(InventoryDetails inventoryDetails) {
        return modelMapper.map(inventoryDetails, InventoryDetailsDTO.class);
    }

    // Convert InventoryDetailsDTO to InventoryDetails entity
    private InventoryDetails convertToModel(InventoryDetailsDTO inventoryDetailsDTO) {
        return modelMapper.map(inventoryDetailsDTO, InventoryDetails.class);
    }

    // Convert List<InventoryDetails> to List<InventoryDetailsDTO>
    private List<InventoryDetailsDTO> convertToListDTO(List<InventoryDetails> inventoryDetailsList) {
        return inventoryDetailsList.stream()
                .map(this::convertToDTO)  // Convert each InventoryDetails entity to DTO
                .collect(Collectors.toList());
    }

    // Generate a unique ID for new inventory details
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update InventoryDetails entity
    private InventoryDetails save(InventoryDetailsDTO inventoryDetailsDTO) {
        try {
            log.info("Saving inventory details");
            InventoryDetails inventoryDetails = InventoryDetails.builder()
                    .id(inventoryDetailsDTO.getId() == null ? generateId() : inventoryDetailsDTO.getId())  // Generate ID if null
                    .priceProduct(inventoryDetailsDTO.getPriceProduct())
                    .totalPrice(inventoryDetailsDTO.getTotalPrice())
                    .idProduct(inventoryDetailsDTO.getIdProduct())
                    .quantity(inventoryDetailsDTO.getQuantity())
                    .idInventory(inventoryDetailsDTO.getIdInventory())
                    .build();
            return inventoryDetailRepository.save(inventoryDetails);
        } catch (DataIntegrityViolationException e) {
            log.error("Save inventory details failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while save inventory details: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public InventoryDetailsDTO findById(Integer id) {
        log.info("Find inventory detail by id: {}", id);
        InventoryDetails inventoryDetails = inventoryDetailRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.INVENTORY_DETAILS_NOT_FOUND));
        return convertToDTO(inventoryDetails);
    }

    @Override
    public InventoryDetailsDTO create(InventoryDetailsDTO inventoryDetailsDTO) {
        InventoryDetails savedInventoryDetails = save(inventoryDetailsDTO);
        return convertToDTO(savedInventoryDetails);
    }

    @Override
    public InventoryDetailsDTO update(InventoryDetailsDTO inventoryDetailsDTO) {
        try{
            log.info("Update inventory detail by id: {}", inventoryDetailsDTO.getId());
            InventoryDetailsDTO existingInventoryDetails = findById(inventoryDetailsDTO.getId());
            InventoryDetails updatedInventoryDetails = save(inventoryDetailsDTO);
            return convertToDTO(updatedInventoryDetails);
        } catch (DataIntegrityViolationException e) {
            log.error("Update inventory detail failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while update inventory detail: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            log.info("Delete inventory detail by id: {}", id);
            InventoryDetailsDTO inventoryDetails = findById(id);
            inventoryDetailRepository.deleteById(inventoryDetails.getId());
        } catch (DataIntegrityViolationException e) {
            log.error("Delete inventory detail failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while delete inventory detail: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }


    @Override
    public List<InventoryDetailsDTO> getByIdInventory(Integer id) {
        return List.of();
    }
}
