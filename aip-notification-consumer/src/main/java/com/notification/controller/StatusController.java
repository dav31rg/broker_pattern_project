package com.notification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.dto.ConsumerStatusResponse;
import com.notification.service.NotificationProcessingService;

@RestController
public class StatusController {

    private final NotificationProcessingService notificationProcessingService;

    public StatusController(NotificationProcessingService notificationProcessingService) {
        this.notificationProcessingService = notificationProcessingService;
    }

    @GetMapping("/status")
    public ConsumerStatusResponse status() {
        return notificationProcessingService.status();
    }
}
