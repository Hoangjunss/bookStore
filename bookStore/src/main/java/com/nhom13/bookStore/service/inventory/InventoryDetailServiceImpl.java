package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.inventory.InventoryDetailsDTO;
import com.nhom13.bookStore.model.inventory.InventoryDetails;
import com.nhom13.bookStore.repository.inventory.InventoryDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        InventoryDetails inventoryDetails = InventoryDetails.builder()
                .id(inventoryDetailsDTO.getId() == null ? generateId() : inventoryDetailsDTO.getId())  // Generate ID if null
                .priceProduct(inventoryDetailsDTO.getPriceProduct())
                .totalPrice(inventoryDetailsDTO.getTotalPrice())
                .idProduct(inventoryDetailsDTO.getIdProduct())
                .quantity(inventoryDetailsDTO.getQuantity())
                .idInventory(inventoryDetailsDTO.getIdInventory())
                .build();
        return inventoryDetailRepository.save(inventoryDetails);
    }

    @Override
    public InventoryDetailsDTO findById(Integer id) {
        InventoryDetails inventoryDetails = inventoryDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("InventoryDetails not found"));
        return convertToDTO(inventoryDetails);
    }

    @Override
    public InventoryDetailsDTO create(InventoryDetailsDTO inventoryDetailsDTO) {
        InventoryDetails savedInventoryDetails = save(inventoryDetailsDTO);
        return convertToDTO(savedInventoryDetails);
    }

    @Override
    public InventoryDetailsDTO update(InventoryDetailsDTO inventoryDetailsDTO) {
        InventoryDetails existingInventoryDetails = inventoryDetailRepository.findById(inventoryDetailsDTO.getId())
                .orElseThrow(() -> new RuntimeException("InventoryDetails not found"));
        InventoryDetails updatedInventoryDetails = save(inventoryDetailsDTO);
        return convertToDTO(updatedInventoryDetails);
    }

    @Override
    public void delete(Integer id) {
        InventoryDetails inventoryDetails = inventoryDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("InventoryDetails not found"));
        inventoryDetailRepository.delete(inventoryDetails);
    }


    @Override
    public List<InventoryDetailsDTO> getByIdInventory(Integer id) {
        return List.of();
    }
}
