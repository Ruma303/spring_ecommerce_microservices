package com.ecommerce.orders.services;

import com.ecommerce.orders.dtos.OrderLineItemsDto;
import com.ecommerce.orders.dtos.OrderRequest;
import com.ecommerce.orders.models.Order;
import com.ecommerce.orders.models.OrderLineItems;
import com.ecommerce.orders.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDto()
                .stream().map(this::mapToOrderLineItems)
                .toList();

        order.setOrderLineItems(orderLineItemsList);
        orderRepository.save(order);
    }

public OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }

}
