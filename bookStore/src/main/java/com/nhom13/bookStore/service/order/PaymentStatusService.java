package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.PaymentStatusDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentStatusService {
    PaymentStatusDTO findById(Integer id);
    PaymentStatusDTO create(PaymentStatusDTO paymentStatusDTO);
    PaymentStatusDTO update(PaymentStatusDTO paymentStatusDTO);
    void delete(Integer id);
}
