package com.ecommerce.orders.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private String orderNumber;
    private List<OrderLineItemsDto> orderLineItemsDto;
}
