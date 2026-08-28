package com.gisoo.marketplace.checkout;

import com.gisoo.marketplace.cart.CartItem;
import com.gisoo.marketplace.cart.CartItemRepository;
import com.gisoo.marketplace.order.*;
import com.gisoo.marketplace.product.Product;
import com.gisoo.marketplace.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckoutService {
    private final CartItemRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;

    public CheckoutService(CartItemRepository cartRepository, ProductRepository productRepository, OrderService orderService,
                           OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository; this.productRepository = productRepository; this.orderService = orderService;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public MarketplaceOrder checkout(Long customerId) {
        List<CartItem> cart = cartRepository.findAllByCustomerIdOrderByCreatedAtAsc(customerId);
        if (cart.isEmpty()) throw new IllegalArgumentException("Cart is empty");

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new IllegalArgumentException("Product does not exist"));
            if (!product.isActive() || product.getStock() < item.getQuantity()) throw new IllegalArgumentException("Product is unavailable for checkout");
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        MarketplaceOrder order = orderService.create(customerId, total);
        for (CartItem item : cart) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow();
            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            product.decreaseStock(item.getQuantity());
            productRepository.save(product);
            orderItemRepository.save(new OrderItem(order.getId(), product.getId(), product.getSellerId(), item.getQuantity(), product.getPrice(), lineTotal));
        }
        cartRepository.deleteAllByCustomerId(customerId);
        return order;
    }
}
