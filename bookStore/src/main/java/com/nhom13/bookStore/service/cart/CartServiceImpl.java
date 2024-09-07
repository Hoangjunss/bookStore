package com.nhom13.bookStore.service.cart;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom13.bookStore.dto.cart.CartDTO;
import com.nhom13.bookStore.model.cart.Cart;
import com.nhom13.bookStore.repository.cart.CartRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
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
        Cart cart = Cart.builder()
                .id(getGenerationId())
                .idUser(cartDTO.getIdUser())
                .totalQuantity(cartDTO.getTotalQuantity())
                .totalPrice(cartDTO.getTotalPrice())
                .build();
        return cartRepository.save(cart);
    }

    @Override
    public CartDTO findById(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return convertToDTO(cart);
    }

    @Override
    public CartDTO findByIdCustomer(Integer idUser) {
        Cart cart = cartRepository.findByIdUser(idUser);
        return convertToDTO(cart);
    }

    @Override
    public CartDTO create(CartDTO cartDTO) {
        return convertToDTO(save(cartDTO));
    }

    @Override
    public CartDTO update(CartDTO cartDTO) {
        return convertToDTO(
            cartRepository
            .save(
                convertToModel(cartDTO)));
    }

    @Override
    public void delete(Integer id) {
        Cart cart = convertToModel(findById(id));
        cartRepository.delete(cart);
    }
    
}
