package com.gisoo.marketplace.commission;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommissionRepository extends JpaRepository<Commission, Long> {
    List<Commission> findAllBySellerIdAndStatus(Long sellerId, CommissionStatus status);
}
