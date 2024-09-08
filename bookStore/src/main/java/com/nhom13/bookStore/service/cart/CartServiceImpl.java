package com.nhom13.bookStore.service.cart;

import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import com.nhom13.bookStore.dto.cart.CartDTO;
import com.nhom13.bookStore.model.cart.Cart;
import com.nhom13.bookStore.repository.cart.CartRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@Slf4j
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Convert Cart entity to CartDTO
    private CartDTO convertToDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }

    // Convert CartDTO to Cart entity
    private Cart convertToModel(CartDTO cartDTO) {
        return modelMapper.map(cartDTO, Cart.class);
    }

    // Convert List<Cart> to List<CartDTO>
    private List<CartDTO> convertToDTOList(List<Cart> cartList) {
        return cartList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert new Cart entity
    private Cart save(CartDTO cartDTO) {
        try {
            log.info("Saving Cart");
            Cart cart = Cart.builder()
                    .id(getGenerationId())
                    .idUser(cartDTO.getIdUser())
                    .totalQuantity(cartDTO.getTotalQuantity())
                    .totalPrice(cartDTO.getTotalPrice())
                    .build();
            return cartRepository.save(cart);
        } catch (DataIntegrityViolationException e) {
            log.error("Save cart failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while save Cart: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public CartDTO findById(Integer id) {
        log.info("Find Cart by id: {}", id);
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.CART_NOT_FOUND));
        return convertToDTO(cart);
    }

    @Override
    public CartDTO findByIdCustomer(Integer idUser) {
        try {
            log.info("Find Cart by user id: {}", idUser);
            Cart cart = cartRepository.findByIdUser(idUser).get(0);
            return convertToDTO(cart);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public CartDTO create(CartDTO cartDTO) {
        return convertToDTO(save(cartDTO));
    }

    @Override
    public CartDTO update(CartDTO cartDTO) {
        try {
            log.info("Updating Cart id: {}", cartDTO.getId());
            return convertToDTO(
                    cartRepository
                            .save(
                                    convertToModel(cartDTO)));
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
            Cart cart = convertToModel(findById(id));
            cartRepository.delete(cart);
        }  catch (DataIntegrityViolationException e) {
            log.error("Delete failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while delete CartDetails: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }
}
