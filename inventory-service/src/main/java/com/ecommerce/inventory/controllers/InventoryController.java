package com.ecommerce.inventory.controllers;

import com.ecommerce.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String checkInventory(@PathVariable("skuCode") String skuCode) {
        boolean check = inventoryService.isInStock(skuCode);
        if (check) return "Inventory check completed";
        else return "Inventory check failed";
    }
}
