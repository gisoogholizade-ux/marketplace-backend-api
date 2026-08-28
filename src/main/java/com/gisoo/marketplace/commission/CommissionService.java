package com.gisoo.marketplace.commission;

import com.gisoo.marketplace.order.OrderItem;
import com.gisoo.marketplace.order.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CommissionService {
    private static final BigDecimal RATE = new BigDecimal("0.10");
    private final CommissionRepository commissionRepository;
    private final OrderItemRepository orderItemRepository;

    public CommissionService(CommissionRepository commissionRepository, OrderItemRepository orderItemRepository) {
        this.commissionRepository = commissionRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public void createForOrder(Long orderId) {
        if (commissionRepository.existsByOrderId(orderId)) return;
        List<OrderItem> items = orderItemRepository.findAllByOrderId(orderId);
        for (OrderItem item : items) {
            BigDecimal amount = item.getLineTotal().multiply(RATE).setScale(2, RoundingMode.HALF_UP);
            commissionRepository.save(new Commission(orderId, item.getSellerId(), amount));
        }
    }
}
