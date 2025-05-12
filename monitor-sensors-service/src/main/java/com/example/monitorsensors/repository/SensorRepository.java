package com.example.monitorsensors.repository;

import com.example.monitorsensors.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByNameContainingIgnoreCaseAndModelContainingIgnoreCase(String sensorName, String model);
}
