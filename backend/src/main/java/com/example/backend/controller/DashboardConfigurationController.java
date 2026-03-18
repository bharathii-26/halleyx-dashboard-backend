package com.example.backend.controller;

import com.example.backend.model.DashboardConfiguration;
import com.example.backend.service.DashboardConfigurationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard-config")
@CrossOrigin(origins = "*")
public class DashboardConfigurationController {

    private final DashboardConfigurationService dashboardConfigurationService;

    public DashboardConfigurationController(DashboardConfigurationService dashboardConfigurationService) {
        this.dashboardConfigurationService = dashboardConfigurationService;
    }

    @GetMapping
    public Map<String, Object> getDashboardConfiguration() {
        DashboardConfiguration configuration = dashboardConfigurationService.getDefaultConfiguration();
        return Map.of(
                "id", configuration.getId(),
                "configKey", configuration.getConfigKey(),
                "widgetsJson", configuration.getWidgetsJson(),
                "updatedAt", configuration.getUpdatedAt()
        );
    }

    @PutMapping
    public Map<String, Object> saveDashboardConfiguration(@RequestBody Map<String, Object> request) {
        Object widgetsJson = request.get("widgetsJson");
        DashboardConfiguration configuration = dashboardConfigurationService.saveDefaultConfiguration(
                widgetsJson == null ? "[]" : String.valueOf(widgetsJson)
        );

        return Map.of(
                "id", configuration.getId(),
                "configKey", configuration.getConfigKey(),
                "widgetsJson", configuration.getWidgetsJson(),
                "updatedAt", configuration.getUpdatedAt() == null ? LocalDateTime.now() : configuration.getUpdatedAt()
        );
    }
}
