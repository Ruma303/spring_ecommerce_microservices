package com.ecommerce.inventory;

import com.ecommerce.inventory.models.Inventory;
import com.ecommerce.inventory.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            inventoryRepository.save(new Inventory(null, "iphone_13", 100));
            inventoryRepository.save(new Inventory(null, "iphone_14", 0));
            inventoryRepository.save(new Inventory(null, "iphone_15", 50));
        };
    }

}
