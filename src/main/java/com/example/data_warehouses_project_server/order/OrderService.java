package com.example.data_warehouses_project_server.order;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.data_warehouses_project_server.domain.oltp.constant.OrderStatus;
import com.example.data_warehouses_project_server.domain.oltp.entity.AddressEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.DeliveryEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.OrderEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.OrderItemEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.PriceEntity;
import com.example.data_warehouses_project_server.domain.oltp.repository.AddressRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.DeliveryRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.OrderItemRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.OrderRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.PriceRepository;
import com.example.data_warehouses_project_server.exception.NotFoundException;

import jakarta.transaction.Transactional;

@Service
class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final PriceRepository priceRepository;

    OrderService(
            OrderRepository orderRepository,
            DeliveryRepository deliveryRepository,
            OrderItemRepository orderItemRepository,
            AddressRepository addressRepository,
            PriceRepository priceRepository
    ) {
        this.orderRepository = orderRepository;
        this.deliveryRepository = deliveryRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;
        this.priceRepository = priceRepository;
    }

    List<OrderResponse> getAllOrders() {
        return this.orderRepository.findAllWithDeliveryAndOrderItems().stream()
                .map(OrderResponse::fromEntity).toList();
    }

    OrderResponse getOrderById(Long id) {
        return this.orderRepository.findById(id)
                .map(OrderResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Transactional
    Long createOrder(CreateOrderForm form) {
        AddressEntity address = this.addressRepository.findByIdAndCustomerId(form.getAddressId(), form.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Address not found"));

        OrderEntity order = this.orderRepository.save(new OrderEntity(
                OffsetDateTime.now(),
                OrderStatus.CREATED,
                form.getPaymentMethod(),
                address.getCustomer()
        ));

        this.deliveryRepository.save(new DeliveryEntity(
                null,
                null,
                order,
                address
        ));

        for (CreateOrderForm.FormOrderItem formOrderItem : form.getOrderItems()) {
            PriceEntity price = this.priceRepository.findByIdAndProductId(formOrderItem.getPriceId(), formOrderItem.getProductId())
                    .orElseThrow(() -> new NotFoundException("Price not found"));

            this.orderItemRepository.save(new OrderItemEntity(
                    formOrderItem.getQuantity(),
                    order,
                    price.getProduct(),
                    price
            ));
        }

        return order.getId();
    }

    @Transactional
    void deleteOrder(Long id) {
        this.orderItemRepository.deleteAllByOrderId(id);
        this.deliveryRepository.deleteAllByOrderId(id);
        this.orderRepository.deleteById(id);
    }

    @Transactional
    void updateStatus(Long id, OrderStatus status) {
        OrderEntity order = this.orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        order.setStatus(status);
    }

    @Transactional
    void updateDelivery(Long id, String shipper, String awb) {
        OrderEntity order = this.orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        DeliveryEntity delivery = order.getDelivery();
        delivery.setShippedBy(shipper);
        delivery.setAwb(awb);
    }
}
