package com.gisoo.marketplace.settlement;

import com.gisoo.marketplace.commission.Commission;
import com.gisoo.marketplace.commission.CommissionRepository;
import com.gisoo.marketplace.commission.CommissionStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class SettlementService {
    private final CommissionRepository commissionRepository;
    private final SettlementRepository settlementRepository;
    public SettlementService(CommissionRepository commissionRepository, SettlementRepository settlementRepository) {
        this.commissionRepository = commissionRepository; this.settlementRepository = settlementRepository;
    }

    @Transactional
    public Settlement create(Long sellerId) {
        List<Commission> pending = commissionRepository.findAllBySellerIdAndStatus(sellerId, CommissionStatus.PENDING);
        if (pending.isEmpty()) throw new IllegalArgumentException("No pending commission for seller");
        BigDecimal amount = pending.stream().map(Commission::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        pending.forEach(Commission::markSettled);
        commissionRepository.saveAll(pending);
        return settlementRepository.save(new Settlement(sellerId, amount));
    }

    @Transactional(readOnly = true)
    public List<Settlement> list(Long sellerId) { return settlementRepository.findAllBySellerIdOrderByCreatedAtDesc(sellerId); }
}
