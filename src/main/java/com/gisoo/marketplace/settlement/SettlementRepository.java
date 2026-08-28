package com.gisoo.marketplace.settlement;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    List<Settlement> findAllBySellerIdOrderByCreatedAtDesc(Long sellerId);
}
