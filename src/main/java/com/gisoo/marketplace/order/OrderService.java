package com.gisoo.marketplace.order;

import com.gisoo.marketplace.user.Role;
import com.gisoo.marketplace.user.User;
import com.gisoo.marketplace.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public MarketplaceOrder create(Long customerId, BigDecimal totalAmount) {
        if (customerId == null) throw new IllegalArgumentException("Customer is required");
        if (totalAmount == null || totalAmount.signum() <= 0) {
            throw new IllegalArgumentException("Order total must be greater than zero");
        }

        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));
        if (customer.getRole() != Role.CUSTOMER && customer.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("User is not allowed to create customer orders");
        }

        return orderRepository.save(new MarketplaceOrder(customerId, totalAmount));
    }

    @Transactional(readOnly = true)
    public List<MarketplaceOrder> forCustomer(Long customerId) {
        if (customerId == null || !userRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer does not exist");
        }
        return orderRepository.findAllByCustomerIdOrderByCreatedAtDesc(customerId);
    }
}
