package com.gisoo.marketplace.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByCustomerIdOrderByCreatedAtAsc(Long customerId);
    Optional<CartItem> findByCustomerIdAndProductId(Long customerId, Long productId);
    void deleteAllByCustomerId(Long customerId);
}
