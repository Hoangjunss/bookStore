package com.nhom13.bookStore.service.cart;

import java.util.List;

import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nhom13.bookStore.dto.cart.CartDTO;
import com.nhom13.bookStore.dto.cart.CartDetailsDTO;
import com.nhom13.bookStore.model.cart.CartDetails;
import com.nhom13.bookStore.repository.cart.CartDetailRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;
import java.util.UUID;

@Service
@Slf4j
public class CartDetailsServiceImpl implements CartDetailsService{
    @Autowired
    private CartDetailRepository cartDetailsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartService cartService;
    
    // Convert CartDetails entity to CartDetailsDTO
    private CartDetailsDTO convertToDTO(CartDetails cartDetails) {
        return modelMapper.map(cartDetails, CartDetailsDTO.class);
    }

    // Convert CartDetailsDTO to CartDetails entity
    private CartDetails convertToModel(CartDetailsDTO cartDetailsDTO) {
        return modelMapper.map(cartDetailsDTO, CartDetails.class);
    }

    // Tính tổng tiền của giỏ hàng
    private Long calculateTotalPrice(Integer idCart) {
        List<CartDetailsDTO> cartDetailsList = getCartDetailsByIdCart(idCart);
        if (cartDetailsList == null || cartDetailsList.isEmpty()) {
            return 0L;
        }
        return cartDetailsList.stream().mapToLong(cd -> cd.getTotalPrice() != null ? cd.getTotalPrice() : 0L).sum();
    }
    private Integer calculateTotalQuality(Integer idCart) {
        List<CartDetailsDTO> cartDetailsList = getCartDetailsByIdCart(idCart);
        if (cartDetailsList == null || cartDetailsList.isEmpty()) {
            return 0;
        }
        return cartDetailsList.stream().mapToInt(cd -> cd.getQuantity() != null ? cd.getQuantity() : 0).sum();
    }

    // Convert List<CartDetails> to List<CartDetailsDTO>
    private List<CartDetailsDTO> convertToDTOList(List<CartDetails> cartDetailsList) {
        return cartDetailsList.stream()
                .map(this::convertToDTO) // map each CartDetails entity to DTO
                .collect(Collectors.toList());
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert new CartDetails entity
    private CartDetails save(CartDetailsDTO cartDetailsDTO) {
        try{
            log.info("Saving CartDetails");
            CartDetails cartDetails = CartDetails.builder()
                    .id(getGenerationId())
                    .priceProduct(cartDetailsDTO.getPriceProduct())
                    .totalPrice(cartDetailsDTO.getTotalPrice())
                    .idProduct(cartDetailsDTO.getIdProduct())
                    .quantity(cartDetailsDTO.getQuantity())
                    .idCart(cartDetailsDTO.getIdCart())
                    .build();
            return cartDetailsRepository.save(cartDetails);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while saving CartDetails: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while saving CartDetails: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }


    @Override
    public CartDetailsDTO findById(Integer id) {
        log.info("Find CartDetails by id: {}", id);
        return convertToDTO(
            cartDetailsRepository
            .findById(id)
            .orElseThrow(()-> new CustomException(Error.CART_DETAILS_NOT_FOUND)));
    }

    @Override
    public CartDetailsDTO create(CartDetailsDTO cartDetailsDTO,Integer idUser) {
        CartDTO cartDTO = cartService.findByIdCustomer(idUser);
        log.info("Cart Detail: {}", cartDTO.toString());
        if(cartDTO == null){
            CartDTO cartDTO1 = CartDTO.builder()
                .id(getGenerationId())
                .idUser(idUser)
                .totalPrice(0L)
                .build();
            cartDetailsDTO.setIdCart(cartService.create(cartDTO1).getId());
        }
        else{
            cartDetailsDTO.setIdCart(cartDTO.getId());
        }
        CartDetails savedCartDetails = save(cartDetailsDTO);
        cartDTO.setTotalPrice(calculateTotalPrice(cartDetailsDTO.getIdCart()));
        cartService.update(cartDTO);
        return convertToDTO(savedCartDetails);
    }

    @Override   
    public CartDetailsDTO update(CartDetailsDTO cartDetailsDTO) {
        log.info("Update CartDetails id: {}", cartDetailsDTO.getId());
        try {
            updateCartTotal(cartDetailsDTO.getIdCart()); // Cập nhật tổng tiền và số lượng của giỏ hàng
            return convertToDTO(
                    cartDetailsRepository.save(
                            convertToModel(cartDetailsDTO)));
        } catch (DataIntegrityViolationException e) {
            log.error("Update failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while update CartDetails: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            log.info("Delete CartDetails by id: {}", id);
            cartDetailsRepository
                    .delete(
                            convertToModel(findById(id)));
        } catch (DataIntegrityViolationException e) {
            log.error("Delete failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while delete CartDetails: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public List<CartDetailsDTO> getCartDetailsByIdCart(Integer id) {
        List<CartDetails> cartDetailsList = cartDetailsRepository.findByIdCart(id);
        return convertToDTOList(cartDetailsList);
    }

    // Cập nhật tổng tiền và số lượng của giỏ hàng
    public void updateCartTotal(Integer idCart) {
        CartDTO cartDTO = cartService.findById(idCart);
        if(cartDTO != null){
            cartDTO.setTotalPrice(calculateTotalPrice(idCart));
            cartDTO.setTotalQuantity(calculateTotalQuality(idCart));
            cartService.update(cartDTO);
        }
        else{
            log.error("Cart not found");
        }
    }
    
}
