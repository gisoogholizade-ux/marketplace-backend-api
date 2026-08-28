package com.gisoo.marketplace.cart;

import com.gisoo.marketplace.product.Product;
import com.gisoo.marketplace.product.ProductRepository;
import com.gisoo.marketplace.user.Role;
import com.gisoo.marketplace.user.User;
import com.gisoo.marketplace.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CartService {
    private final CartItemRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartItemRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository; this.productRepository = productRepository; this.userRepository = userRepository;
    }

    @Transactional
    public CartItem add(Long customerId, Long productId, int quantity) {
        User customer = userRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));
        if (customer.getRole() != Role.CUSTOMER) throw new IllegalArgumentException("User is not a customer");
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product does not exist"));
        if (!product.isActive()) throw new IllegalArgumentException("Product is inactive");
        if (quantity <= 0 || quantity > product.getStock()) throw new IllegalArgumentException("Requested quantity is unavailable");

        CartItem item = cartRepository.findByCustomerIdAndProductId(customerId, productId)
                .orElseGet(() -> new CartItem(customerId, productId, 0));
        if (item.getQuantity() + quantity > product.getStock()) throw new IllegalArgumentException("Requested quantity exceeds stock");
        item.addQuantity(quantity);
        return cartRepository.save(item);
    }

    @Transactional(readOnly = true)
    public List<CartItem> list(Long customerId) {
        return cartRepository.findAllByCustomerIdOrderByCreatedAtAsc(customerId);
    }
}
