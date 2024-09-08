package com.nhom13.bookStore.service.cart;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        CartDetails cartDetails = CartDetails.builder()
                .id(getGenerationId())
                .priceProduct(cartDetailsDTO.getPriceProduct())
                .totalPrice(cartDetailsDTO.getTotalPrice())
                .idProduct(cartDetailsDTO.getIdProduct())
                .quantity(cartDetailsDTO.getQuantity())
                .idCart(cartDetailsDTO.getIdCart())
                .build();
        return cartDetailsRepository.save(cartDetails);
    }


    @Override
    public CartDetailsDTO findById(Integer id) {
        return convertToDTO(
            cartDetailsRepository
            .findById(id)
            .orElseThrow());
    }

    @Override
    public CartDetailsDTO create(CartDetailsDTO cartDetailsDTO,Integer idUser) {
        CartDTO cartDetails = cartService.findByIdCustomer(idUser);
        if(cartDetails == null){
            CartDTO cartDTO = CartDTO.builder()
                .id(getGenerationId())
                .idUser(idUser)
                .build();
            cartDetailsDTO.setIdCart(cartService.create(cartDTO).getId());
        }
        else{
            cartDetailsDTO.setIdCart(cartDetails.getId());
        }
        return convertToDTO(save(cartDetailsDTO));
    }

    @Override   
    public CartDetailsDTO update(CartDetailsDTO cartDetailsDTO) {
        return convertToDTO(
            cartDetailsRepository.save(
                convertToModel(cartDetailsDTO)));
    }

    @Override
    public void delete(Integer id) {
        cartDetailsRepository
            .delete(
                convertToModel(findById(id)));
    }

    @Override
    public List<CartDetailsDTO> getCartDetailsByIdCart(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCartDetailsByIdCart'");
    }
    
}
