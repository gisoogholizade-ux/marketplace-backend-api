package com.gisoo.marketplace.payment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock PaymentRepository paymentRepository;
    @InjectMocks PaymentService paymentService;

    @Test
    void rejectsNonPositiveAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.create(1L, BigDecimal.ZERO, "tx-1"));
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void rejectsMissingOrder() {
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.create(null, BigDecimal.TEN, "tx-1"));
        verify(paymentRepository, never()).save(any());
    }
}
