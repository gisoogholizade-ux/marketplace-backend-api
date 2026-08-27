package com.gisoo.marketplace.payment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment create(Long orderId, BigDecimal amount, String transactionReference) {
        if (orderId == null) throw new IllegalArgumentException("Order is required");
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
        String reference = transactionReference == null ? null : transactionReference.trim();
        return paymentRepository.save(new Payment(orderId, amount, reference));
    }

    @Transactional(readOnly = true)
    public List<Payment> forOrder(Long orderId) {
        return paymentRepository.findAllByOrderIdOrderByCreatedAtDesc(orderId);
    }
}
