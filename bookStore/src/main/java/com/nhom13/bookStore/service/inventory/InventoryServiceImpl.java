package com.nhom13.bookStore.service.inventory;

import com.nhom13.bookStore.dto.cart.CartDetailsDTO;
import com.nhom13.bookStore.dto.inventory.InventoryDTO;
import com.nhom13.bookStore.dto.inventory.InventoryDetailsDTO;
import com.nhom13.bookStore.dto.order.OrderDetailsDTO;
import com.nhom13.bookStore.dto.order.OrdersDTO;
import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import com.nhom13.bookStore.model.inventory.Inventory;
import com.nhom13.bookStore.repository.inventory.InventoryRepository;
import com.nhom13.bookStore.service.order.OrderDetailService;
import com.nhom13.bookStore.service.order.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InventoryDetailService inventoryDetailService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailService orderDetailService;

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
    public List<InventoryDTO> convertToDtoList(List<Inventory> ordersDetailsList) {
        return ordersDetailsList.stream()
                .map(product -> modelMapper.map(product, InventoryDTO.class))
                .collect(Collectors.toList());
    }


    // Save method to insert or update Inventory entity
    private Inventory save(InventoryDTO inventoryDTO) {
        try {
            Inventory inventory = Inventory.builder()
                    .id(inventoryDTO.getId() == null ? generateId() : inventoryDTO.getId())  // Generate ID if null
                    .idUser(inventoryDTO.getIdUser())
                    .createDay(LocalDate.now())
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
    private InventoryDTO conventToInventory(OrdersDTO ordersDTO){
        return InventoryDTO.builder()
                .idUser(ordersDTO.getIdUser())
                .totalPrice(ordersDTO.getTotalPrice())
                .totalQuantity(ordersDTO.getTotalQuantity())
                .build();
    }
    private List<InventoryDetailsDTO> conventToOrderDetailDTO(List<OrderDetailsDTO> orderDetailsDTOS, Integer idInventory){
        return orderDetailsDTOS.stream()
                .map(product ->
                        inventoryDetailService.create( InventoryDetailsDTO.builder()
                                .idInventory(idInventory)
                                .idProduct(product.getIdProduct())
                                .priceProduct(product.getPriceProduct())
                                .totalPrice(product.getTotalPrice())
                                .quantity(product.getQuantity())
                                .build()
                        ))
                .collect(Collectors.toList());
    }
    @Override
    public InventoryDTO findById(Integer id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.INVENTORY_NOT_FOUND));
        return convertToDTO(inventory);
    }

    @Override
    public InventoryDTO create(Integer idOrder) {
        OrdersDTO ordersDTO=ordersService.findById(idOrder);
        InventoryDTO inventoryDTO=conventToInventory(ordersDTO);
        Inventory savedInventory = save(inventoryDTO);
        List<OrderDetailsDTO> orderDetailsDTOS=orderDetailService.getAll(ordersDTO.getId());
        conventToOrderDetailDTO(orderDetailsDTOS,savedInventory.getId());
        return convertToDTO(savedInventory);
    }

    @Override
    public InventoryDTO update(InventoryDTO inventoryDTO) {
        try {
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

    @Override
    public List<InventoryDTO> findByIdUser(Integer id) {
        return convertToDtoList(inventoryRepository.findByIdUser(id));
    }
}
