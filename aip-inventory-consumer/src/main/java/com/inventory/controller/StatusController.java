package com.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.dto.ConsumerStatusResponse;
import com.inventory.service.InventoryProcessingService;

@RestController
public class StatusController {

    private final InventoryProcessingService inventoryProcessingService;

    public StatusController(InventoryProcessingService inventoryProcessingService) {
        this.inventoryProcessingService = inventoryProcessingService;
    }

    @GetMapping("/status")
    public ConsumerStatusResponse status() {
        return inventoryProcessingService.status();
    }
}
