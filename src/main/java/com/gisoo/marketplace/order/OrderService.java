package com.gisoo.marketplace.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public MarketplaceOrder create(Long customerId, BigDecimal totalAmount) {
        if (customerId == null) throw new IllegalArgumentException("Customer is required");
        if (totalAmount == null || totalAmount.signum() <= 0) {
            throw new IllegalArgumentException("Order total must be greater than zero");
        }
        return orderRepository.save(new MarketplaceOrder(customerId, totalAmount));
    }

    @Transactional(readOnly = true)
    public List<MarketplaceOrder> forCustomer(Long customerId) {
        return orderRepository.findAllByCustomerIdOrderByCreatedAtDesc(customerId);
    }
}
