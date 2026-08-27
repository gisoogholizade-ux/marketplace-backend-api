package com.gisoo.marketplace.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<MarketplaceOrder, Long> {
    List<MarketplaceOrder> findAllByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
