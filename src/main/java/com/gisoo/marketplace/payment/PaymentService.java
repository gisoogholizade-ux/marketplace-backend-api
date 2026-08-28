package com.gisoo.marketplace.payment;

import com.gisoo.marketplace.commission.CommissionService;
import com.gisoo.marketplace.order.MarketplaceOrder;
import com.gisoo.marketplace.order.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final CommissionService commissionService;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository, CommissionService commissionService) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.commissionService = commissionService;
    }

    @Transactional
    public Payment create(Long orderId, BigDecimal amount, String transactionReference) {
        if (orderId == null) throw new IllegalArgumentException("Order is required");
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Payment amount must be greater than zero");
        MarketplaceOrder order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order does not exist"));
        if (amount.compareTo(order.getTotalAmount()) != 0) throw new IllegalArgumentException("Payment amount must match order total");

        String reference = transactionReference == null ? null : transactionReference.trim();
        if (reference != null && reference.isBlank()) reference = null;
        if (reference != null && paymentRepository.existsByTransactionReference(reference)) throw new IllegalArgumentException("Transaction reference already exists");
        return paymentRepository.save(new Payment(orderId, amount, reference));
    }

    @Transactional
    public Payment capture(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Payment does not exist"));
        MarketplaceOrder order = orderRepository.findById(payment.getOrderId()).orElseThrow(() -> new IllegalArgumentException("Order does not exist"));
        payment.markSucceeded();
        order.markPaid();
        paymentRepository.save(payment);
        orderRepository.save(order);
        commissionService.createForOrder(order.getId());
        return payment;
    }

    @Transactional(readOnly = true)
    public List<Payment> forOrder(Long orderId) {
        if (orderId == null || !orderRepository.existsById(orderId)) throw new IllegalArgumentException("Order does not exist");
        return paymentRepository.findAllByOrderIdOrderByCreatedAtDesc(orderId);
    }
}
