package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.cart.CartDTO;
import com.nhom13.bookStore.dto.cart.CartDetailsDTO;
import com.nhom13.bookStore.dto.order.OrderDetailsDTO;
import com.nhom13.bookStore.dto.order.OrdersDTO;
import com.nhom13.bookStore.dto.product.ProductDTO;
import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import com.nhom13.bookStore.model.cart.Cart;
import com.nhom13.bookStore.model.order.OrderDetail;
import com.nhom13.bookStore.model.order.Orders;
import com.nhom13.bookStore.model.product.Product;
import com.nhom13.bookStore.repository.order.OrderRepository;
import com.nhom13.bookStore.service.cart.CartDetailsService;
import com.nhom13.bookStore.service.cart.CartService;
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
public class OrdersServiceImpl implements OrdersService{
    @Autowired
    private OrderRepository ordersRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CartDetailsService cartDetailsService;

    // Convert Orders entity to OrdersDTO
    private OrdersDTO convertToDTO(Orders orders) {
        return modelMapper.map(orders, OrdersDTO.class);
    }

    // Convert OrdersDTO to Orders entity
    private Orders convertToModel(OrdersDTO ordersDTO) {
        return modelMapper.map(ordersDTO, Orders.class);
    }
    public List<OrdersDTO> convertToDtoList(List<Orders> ordersList) {
        return ordersList.stream()
                .map(product -> modelMapper.map(product, OrdersDTO.class))
                .collect(Collectors.toList());
    }

    // Generate a unique ID for new orders
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update Orders entity
    private Orders save(OrdersDTO ordersDTO) {
        try {
            log.info("Saving Orders");
            Orders orders = Orders.builder()
                    .id(ordersDTO.getId() == null ? generateId() : ordersDTO.getId())  // Generate ID if null
                    .idUser(ordersDTO.getIdUser())
                    .createDay(ordersDTO.getCreateDay())
                    .totalPrice(ordersDTO.getTotalPrice())
                    .totalQuantity(ordersDTO.getTotalQuantity())
                    .paymentStatus(ordersDTO.getPaymentStatus())
                    .idOrderStatus(ordersDTO.getIdOrderStatus())
                    .build();
            return ordersRepository.save(orders);
        }  catch (DataIntegrityViolationException e) {
            log.error("Save order failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while save order: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }
    private OrdersDTO conventToOrder(CartDTO cartDTO){
        return  OrdersDTO.builder()
                .idUser(cartDTO.getIdUser())
                .idOrderStatus(1)
                .createDay(LocalDate.now())
                .paymentStatus(1)
                .totalPrice(cartDTO.getTotalPrice())
                .totalQuantity(cartDTO.getTotalQuantity()).build();
    }
    private List<OrderDetailsDTO> conventToOrderDetailDTO(List<CartDetailsDTO> cartDetailsDTOS,Integer idOrder){
        return cartDetailsDTOS.stream()
                .map(product ->
                       orderDetailService.create( OrderDetailsDTO.builder().idOrder(idOrder)
                                .idProduct(product.getIdProduct())
                                .priceProduct(product.getPriceProduct())
                                .totalPrice(product.getTotalPrice())
                                .quantity(product.getQuantity()).build()
                        ))
                .collect(Collectors.toList());
    }
    @Override
    public OrdersDTO findById(Integer id) {
        log.info("Find order by id: {}", id);
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.ORDER_NOT_FOUND));
        return convertToDTO(orders);
    }

    @Override
    public List<OrdersDTO> findByIdUser(Integer idUser) {
        log.info("Find all orders by idUser: {}", idUser);
        return convertToDtoList(ordersRepository.findByIdUser(idUser));
    }

    @Override
    public OrdersDTO create(Integer idCart) {
        CartDTO cartDTO=cartService.findById(idCart);
        OrdersDTO ordersDTO=conventToOrder(cartDTO);
        OrdersDTO ordersDTOSave=convertToDTO(save(ordersDTO));
        List<CartDetailsDTO> cartDetailsDTOS=cartDetailsService.getCartDetailsByIdCart(cartDTO.getId());
        conventToOrderDetailDTO(cartDetailsDTOS,ordersDTO.getId());
        return ordersDTOSave ;
    }

    @Override
    public OrdersDTO update(OrdersDTO ordersDTO) {
        try {
            log.info("Update order by id: {}", ordersDTO.getId());
            ordersRepository.findById(ordersDTO.getId());
            Orders updatedOrders = save(ordersDTO);
            return convertToDTO(updatedOrders);
        } catch (DataIntegrityViolationException e) {
            log.error("Update order failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while update order: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            log.info("Delete order by id: {}", id);
            OrdersDTO orders = findById(id);
            ordersRepository.deleteById(orders.getId());
        } catch (DataIntegrityViolationException e) {
            log.error("Delete order failed: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_VALIDATION_ERROR);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while delete order: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public List<OrdersDTO> getAll() {
        return convertToDtoList(ordersRepository.findAll());
    }
}
