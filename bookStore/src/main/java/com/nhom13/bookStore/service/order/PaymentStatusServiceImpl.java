package com.nhom13.bookStore.service.order;

import com.nhom13.bookStore.dto.order.PaymentStatusDTO;
import com.nhom13.bookStore.exception.CustomException;
import com.nhom13.bookStore.exception.Error;
import com.nhom13.bookStore.model.order.PaymentStatus;
import com.nhom13.bookStore.repository.order.PaymentStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PaymentStatusServiceImpl implements  PaymentStatusService{
    @Autowired
    private PaymentStatusRepository paymentStatusRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Convert PaymentStatus entity to PaymentStatusDTO
    private PaymentStatusDTO convertToDTO(PaymentStatus paymentStatus) {
        return modelMapper.map(paymentStatus, PaymentStatusDTO.class);
    }

    // Convert PaymentStatusDTO to PaymentStatus entity
    private PaymentStatus convertToModel(PaymentStatusDTO paymentStatusDTO) {
        return modelMapper.map(paymentStatusDTO, PaymentStatus.class);
    }

    // Generate a unique ID for new payment status
    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    // Save method to insert or update PaymentStatus entity
    private PaymentStatus save(PaymentStatusDTO paymentStatusDTO) {
        try {
            PaymentStatus paymentStatus = PaymentStatus.builder()
                    .id(paymentStatusDTO.getId() == null ? generateId() : paymentStatusDTO.getId()) // Generate ID if null
                    .name(paymentStatusDTO.getName())
                    .build();
            return paymentStatusRepository.save(paymentStatus);
        } catch (DataIntegrityViolationException e) {
            log.error("Unable to save payment status: {}", e.getMessage());
            throw new CustomException(Error.PAYMENT_STATUS_UNABLE_TO_SAVE);
        } catch (DataAccessResourceFailureException e) {
            log.error("Database connection failure while saving payment status: {}", e.getMessage());
            throw new CustomException(Error.MYSQL_CONNECTION_FAILURE);
        }
    }

    @Override
    public PaymentStatusDTO findById(Integer id) {
        try {
            PaymentStatus paymentStatus = paymentStatusRepository.findById(id)
                    .orElseThrow(() -> new CustomException(Error.PAYMENT_STATUS_NOT_FOUND));
            return convertToDTO(paymentStatus);
        } catch (CustomException e) {
            log.error("Error finding payment status by id {}: {}", id, e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public PaymentStatusDTO create(PaymentStatusDTO paymentStatusDTO) {
        try {
            PaymentStatus savedPaymentStatus = save(paymentStatusDTO);
            return convertToDTO(savedPaymentStatus);
        } catch (CustomException e) {
            log.error("Error creating payment status: {}", e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public PaymentStatusDTO update(PaymentStatusDTO paymentStatusDTO) {
        try {
            // Ensure the payment status exists before updating
            paymentStatusRepository.findById(paymentStatusDTO.getId())
                    .orElseThrow(() -> new CustomException(Error.PAYMENT_STATUS_NOT_FOUND));

            PaymentStatus updatedPaymentStatus = save(paymentStatusDTO);
            return convertToDTO(updatedPaymentStatus);
        } catch (CustomException e) {
            log.error("Error updating payment status: {}", e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            PaymentStatus paymentStatus = paymentStatusRepository.findById(id)
                    .orElseThrow(() -> new CustomException(Error.PAYMENT_STATUS_NOT_FOUND));
            paymentStatusRepository.delete(paymentStatus);
        } catch (CustomException e) {
            log.error("Error deleting payment status by id {}: {}", id, e.getMessage());
            throw e; // Rethrow the custom exception to be handled by the caller
        }
    }
}
