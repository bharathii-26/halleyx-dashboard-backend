package com.example.backend.service;

import com.example.backend.model.DashboardConfiguration;
import com.example.backend.repository.DashboardConfigurationRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardConfigurationService {

    private static final String DEFAULT_CONFIG_KEY = "default";

    private final DashboardConfigurationRepository dashboardConfigurationRepository;

    public DashboardConfigurationService(DashboardConfigurationRepository dashboardConfigurationRepository) {
        this.dashboardConfigurationRepository = dashboardConfigurationRepository;
    }

    public DashboardConfiguration getDefaultConfiguration() {
        return dashboardConfigurationRepository.findByConfigKey(DEFAULT_CONFIG_KEY)
                .orElseGet(this::createDefaultConfiguration);
    }

    public DashboardConfiguration saveDefaultConfiguration(String widgetsJson) {
        DashboardConfiguration configuration = dashboardConfigurationRepository.findByConfigKey(DEFAULT_CONFIG_KEY)
                .orElseGet(this::createDefaultConfiguration);

        configuration.setWidgetsJson(widgetsJson);
        return dashboardConfigurationRepository.save(configuration);
    }

    private DashboardConfiguration createDefaultConfiguration() {
        DashboardConfiguration configuration = new DashboardConfiguration();
        configuration.setConfigKey(DEFAULT_CONFIG_KEY);
        configuration.setWidgetsJson("[]");
        return dashboardConfigurationRepository.save(configuration);
    }
}
