package com.example.backend.repository;

import com.example.backend.model.DashboardConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DashboardConfigurationRepository extends JpaRepository<DashboardConfiguration, Long> {

    Optional<DashboardConfiguration> findByConfigKey(String configKey);
}
