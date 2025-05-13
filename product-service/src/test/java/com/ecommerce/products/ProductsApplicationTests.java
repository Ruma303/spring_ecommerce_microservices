package com.ecommerce.products;

import com.ecommerce.products.dtos.ProductRequest;
import com.ecommerce.products.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductsApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.4");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    static void setProperties(DynamicPropertyRegistry dynRegistry) {
        // Inseriamo la stringa di connessione a MongoDB nella configurazione del MongoDBContainer
        dynRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    // Testiamo l'endpoint POST di creazione del prodotto
    @Test
    @DisplayName("Dovrebbe creare un prodotto")
    void shouldCreateProduct() throws Exception {
        // Arrange
        ProductRequest product1 = createProduct();

        // Act
        // Convertiamo il prodotto in JSON
        String product1Json = objectMapper.writeValueAsString(product1);

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType("application/json")
                        .content(product1Json))
                .andExpect(status().isCreated());

        // Verifica che il prodotto sia stato salvato nel database
        Assertions.assertFalse(productRepository.findAll().isEmpty());
    }

    private ProductRequest createProduct() {
        return ProductRequest.builder()
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf((100.0)))
                .build();
    }

    // Testiamo l'endpoint GET di recupero dei prodotti
    @Test
    @DisplayName("Dovrebbe restituire tutti i prodotti")
    void shouldReturnAllProducts() throws Exception {
        // Arrange
        ProductRequest product1 = createProduct();
        ProductRequest product2 = createProduct();
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(BigDecimal.valueOf((200.0)));

        // Act
        String product1Json = objectMapper.writeValueAsString(product1);
        String product2Json = objectMapper.writeValueAsString(product2);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType("application/json")
                        .content(product1Json))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType("application/json")
                        .content(product2Json))
                .andExpect(status().isCreated());

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk());
    }
}