package com.ibrahimkvlci.ecommerce.payment.services;

import com.ibrahimkvlci.ecommerce.payment.models.PaymentMethod;
import com.ibrahimkvlci.ecommerce.payment.dto.PaymentMethodDTO;
import com.ibrahimkvlci.ecommerce.payment.repositories.PaymentMethodRepository;
import com.ibrahimkvlci.ecommerce.payment.exceptions.PaymentMethodNotFoundException;
import com.ibrahimkvlci.ecommerce.payment.exceptions.PaymentMethodValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentMethodServiceImpl implements PaymentMethodService {
    
    private final PaymentMethodRepository paymentMethodRepository;
    
    @Autowired
    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }
    
    @Override
    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new PaymentMethodValidationException("Payment method cannot be null");
        }
        
        if (paymentMethod.getName() == null || paymentMethod.getName().trim().isEmpty()) {
            throw new PaymentMethodValidationException("Payment method name is required");
        }
        
        if (paymentMethodRepository.existsByNameIgnoreCase(paymentMethod.getName())) {
            throw new PaymentMethodValidationException("Payment method with name '" + paymentMethod.getName() + "' already exists");
        }
        
        return paymentMethodRepository.save(paymentMethod);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethod> getPaymentMethodById(Long id) {
        if (id == null) {
            throw new PaymentMethodValidationException("Payment method ID cannot be null");
        }
        return paymentMethodRepository.findById(id);
    }
    
    @Override
    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethod) {
        if (id == null) {
            throw new PaymentMethodValidationException("Payment method ID cannot be null");
        }
        
        if (paymentMethod == null) {
            throw new PaymentMethodValidationException("Payment method cannot be null");
        }
        
        PaymentMethod existingPaymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new PaymentMethodNotFoundException("Payment method with ID " + id + " not found"));
        
        if (paymentMethod.getName() == null || paymentMethod.getName().trim().isEmpty()) {
            throw new PaymentMethodValidationException("Payment method name is required");
        }
        
        // Check if another payment method with the same name exists (excluding current one)
        PaymentMethod existingWithSameName = paymentMethodRepository.findByNameIgnoreCase(paymentMethod.getName());
        if (existingWithSameName != null && !existingWithSameName.getId().equals(id)) {
            throw new PaymentMethodValidationException("Payment method with name '" + paymentMethod.getName() + "' already exists");
        }
        
        existingPaymentMethod.setName(paymentMethod.getName());
        return paymentMethodRepository.save(existingPaymentMethod);
    }
    
    @Override
    public void deletePaymentMethod(Long id) {
        if (id == null) {
            throw new PaymentMethodValidationException("Payment method ID cannot be null");
        }
        
        if (!paymentMethodRepository.existsById(id)) {
            throw new PaymentMethodNotFoundException("Payment method with ID " + id + " not found");
        }
        
        paymentMethodRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethod> getPaymentMethodByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new PaymentMethodValidationException("Payment method name cannot be null or empty");
        }
        PaymentMethod paymentMethod = paymentMethodRepository.findByNameIgnoreCase(name);
        return Optional.ofNullable(paymentMethod);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethod> searchPaymentMethodsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new PaymentMethodValidationException("Search name cannot be null or empty");
        }
        return paymentMethodRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean paymentMethodExistsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new PaymentMethodValidationException("Payment method name cannot be null or empty");
        }
        return paymentMethodRepository.existsByNameIgnoreCase(name);
    }
    
    @Override
    public PaymentMethod mapToEntity(PaymentMethodDTO paymentMethodDTO) {
        if (paymentMethodDTO == null) {
            return null;
        }
        
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(paymentMethodDTO.getId());
        paymentMethod.setName(paymentMethodDTO.getName());
        return paymentMethod;
    }
    
    @Override
    public PaymentMethodDTO mapToDTO(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            return null;
        }
        
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setId(paymentMethod.getId());
        paymentMethodDTO.setName(paymentMethod.getName());
        return paymentMethodDTO;
    }
}
