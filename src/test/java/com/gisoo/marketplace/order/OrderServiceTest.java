package com.gisoo.marketplace.order;

import com.gisoo.marketplace.user.Role;
import com.gisoo.marketplace.user.User;
import com.gisoo.marketplace.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock OrderRepository orderRepository;
    @Mock UserRepository userRepository;
    @InjectMocks OrderService orderService;

    @Test
    void rejectsUnknownCustomer() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,
                () -> orderService.create(99L, BigDecimal.TEN));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void acceptsCustomerRole() {
        User customer = new User("Demo", "demo@example.com", "hash", Role.CUSTOMER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(MarketplaceOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        orderService.create(1L, BigDecimal.TEN);
        verify(orderRepository).save(any(MarketplaceOrder.class));
    }
}
