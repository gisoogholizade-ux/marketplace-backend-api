package com.gisoo.marketplace.settlement;

import com.gisoo.marketplace.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seller/settlements")
public class SettlementController {
    private final SettlementService service;
    public SettlementController(SettlementService service) { this.service = service; }

    @PostMapping("/{sellerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Settlement> create(@PathVariable Long sellerId) {
        return ApiResponse.success("Settlement created", service.create(sellerId));
    }

    @GetMapping("/{sellerId}")
    public ApiResponse<List<Settlement>> list(@PathVariable Long sellerId) {
        return ApiResponse.success("Settlements retrieved", service.list(sellerId));
    }
}
