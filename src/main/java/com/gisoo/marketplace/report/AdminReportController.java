package com.gisoo.marketplace.report;

import com.gisoo.marketplace.common.response.ApiResponse;
import com.gisoo.marketplace.order.OrderRepository;
import com.gisoo.marketplace.payment.Payment;
import com.gisoo.marketplace.payment.PaymentRepository;
import com.gisoo.marketplace.product.ProductRepository;
import com.gisoo.marketplace.user.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/reports")
public class AdminReportController {
    private final UserRepository users;
    private final ProductRepository products;
    private final OrderRepository orders;
    private final PaymentRepository payments;

    public AdminReportController(UserRepository users, ProductRepository products, OrderRepository orders, PaymentRepository payments) {
        this.users = users; this.products = products; this.orders = orders; this.payments = payments;
    }

    @GetMapping("/summary")
    public ApiResponse<Map<String, Object>> summary() {
        BigDecimal paymentVolume = payments.findAll().stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("users", users.count());
        data.put("products", products.count());
        data.put("orders", orders.count());
        data.put("payments", payments.count());
        data.put("paymentVolume", paymentVolume);
        return ApiResponse.success("Marketplace summary generated", data);
    }
}
