package com.example.data_warehouses_project_server.order;

import com.example.data_warehouses_project_server.domain.oltp.constant.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(OrderAdminController.DEFAULT_ENDPOINT_MAPPING)
class OrderAdminController {

    static final String DEFAULT_ENDPOINT_MAPPING = "/api/admin/order";

    private final OrderService orderService;

    OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(this.orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid CreateOrderForm form) {
        Long orderId = this.orderService.createOrder(form);
        return ResponseEntity.created(URI.create(DEFAULT_ENDPOINT_MAPPING + "/" + orderId)).body("Order created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        this.orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable("orderId") Long id, @RequestParam OrderStatus status) {
        this.orderService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<Void> updateDelivery(
            @PathVariable("orderId") Long id,
            @RequestParam String shipper,
            @RequestParam String awb
    ) {
        this.orderService.updateDelivery(id, shipper, awb);
        return ResponseEntity.noContent().build();
    }
}
