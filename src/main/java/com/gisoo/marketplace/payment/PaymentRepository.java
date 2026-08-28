package com.gisoo.marketplace.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByOrderIdOrderByCreatedAtDesc(Long orderId);
    boolean existsByTransactionReference(String transactionReference);
}
