package com.ibrahimkvlci.ecommerce.payment.services;

import com.ibrahimkvlci.ecommerce.payment.models.PaymentDetail;
import com.ibrahimkvlci.ecommerce.payment.models.PaymentMethod;
import com.ibrahimkvlci.ecommerce.payment.dto.PaymentDetailDTO;
import com.ibrahimkvlci.ecommerce.payment.repositories.PaymentDetailRepository;
import com.ibrahimkvlci.ecommerce.payment.repositories.PaymentMethodRepository;
import com.ibrahimkvlci.ecommerce.payment.exceptions.PaymentDetailNotFoundException;
import com.ibrahimkvlci.ecommerce.payment.exceptions.PaymentDetailValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentDetailServiceImpl implements PaymentDetailService {
    
    private final PaymentDetailRepository paymentDetailRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    
    @Autowired
    public PaymentDetailServiceImpl(PaymentDetailRepository paymentDetailRepository, 
                                   PaymentMethodRepository paymentMethodRepository) {
        this.paymentDetailRepository = paymentDetailRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }
    
    @Override
    public PaymentDetail createPaymentDetail(PaymentDetail paymentDetail) {
        // Verify payment method exists
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentDetail.getPaymentMethod().getId())
                .orElseThrow(() -> new PaymentDetailValidationException("Payment method with ID " + paymentDetail.getPaymentMethod().getId() + " not found"));
        
        paymentDetail.setPaymentMethod(paymentMethod);
        
        return paymentDetailRepository.save(paymentDetail);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetail> getAllPaymentDetails() {
        return paymentDetailRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentDetail> getPaymentDetailById(Long id) {
        if (id == null) {
            throw new PaymentDetailValidationException("Payment detail ID cannot be null");
        }
        return paymentDetailRepository.findById(id);
    }
    
    @Override
    public PaymentDetail updatePaymentDetail(Long id, PaymentDetail paymentDetail) {
        
        PaymentDetail existingPaymentDetail = paymentDetailRepository.findById(id)
                .orElseThrow(() -> new PaymentDetailNotFoundException("Payment detail with ID " + id + " not found"));
        
        // Verify payment method exists
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentDetail.getPaymentMethod().getId())
                .orElseThrow(() -> new PaymentDetailValidationException("Payment method with ID " + paymentDetail.getPaymentMethod().getId() + " not found"));
        
        existingPaymentDetail.setOrderId(paymentDetail.getOrderId());
        existingPaymentDetail.setPaymentMethod(paymentMethod);
        existingPaymentDetail.setAmount(paymentDetail.getAmount());
        existingPaymentDetail.setPaid(paymentDetail.isPaid());
        
        return paymentDetailRepository.save(existingPaymentDetail);
    }
    
    @Override
    public void deletePaymentDetail(Long id) {
        if (id == null) {
            throw new PaymentDetailValidationException("Payment detail ID cannot be null");
        }
        
        if (!paymentDetailRepository.existsById(id)) {
            throw new PaymentDetailNotFoundException("Payment detail with ID " + id + " not found");
        }
        
        paymentDetailRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetail> getPaymentDetailsByOrderId(Long orderId) {
        if (orderId == null) {
            throw new PaymentDetailValidationException("Order ID cannot be null");
        }
        return paymentDetailRepository.findByOrderId(orderId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetail> getPaymentDetailsByStatus(boolean isPaid) {
        return paymentDetailRepository.findByIsPaid(isPaid);
    }
    
    @Override
    public PaymentDetail mapToEntity(PaymentDetailDTO paymentDetailDTO) {
        if (paymentDetailDTO == null) {
            return null;
        }
        
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setId(paymentDetailDTO.getId());
        paymentDetail.setOrderId(paymentDetailDTO.getOrderId());
        paymentDetail.setAmount(paymentDetailDTO.getAmount());
        paymentDetail.setPaid(paymentDetailDTO.getIsPaid() != null ? paymentDetailDTO.getIsPaid() : false);
        
        // Set payment method if ID is provided
        if (paymentDetailDTO.getPaymentMethodId() != null) {
            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setId(paymentDetailDTO.getPaymentMethodId());
            paymentDetail.setPaymentMethod(paymentMethod);
        }
        
        return paymentDetail;
    }
    
    @Override
    public PaymentDetailDTO mapToDTO(PaymentDetail paymentDetail) {
        if (paymentDetail == null) {
            return null;
        }
        
        PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO();
        paymentDetailDTO.setId(paymentDetail.getId());
        paymentDetailDTO.setOrderId(paymentDetail.getOrderId());
        paymentDetailDTO.setAmount(paymentDetail.getAmount());
        paymentDetailDTO.setIsPaid(paymentDetail.isPaid());
        
        if (paymentDetail.getPaymentMethod() != null) {
            paymentDetailDTO.setPaymentMethodId(paymentDetail.getPaymentMethod().getId());
        }
        
        return paymentDetailDTO;
    }
}
