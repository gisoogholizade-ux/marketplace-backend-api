package com.gisoo.marketplace.payment;

import com.gisoo.marketplace.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock PaymentRepository paymentRepository;
    @Mock OrderRepository orderRepository;
    @InjectMocks PaymentService paymentService;

    @Test
    void rejectsNonPositiveAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.create(1L, BigDecimal.ZERO, "tx-1"));
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void rejectsMissingOrderId() {
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.create(null, BigDecimal.TEN, "tx-1"));
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void rejectsUnknownOrder() {
        when(orderRepository.existsById(77L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.create(77L, BigDecimal.TEN, "tx-77"));
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void rejectsDuplicateTransactionReference() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(paymentRepository.existsByTransactionReference("tx-1")).thenReturn(true);
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.create(1L, BigDecimal.TEN, "tx-1"));
        verify(paymentRepository, never()).save(any());
    }
}
