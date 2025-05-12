package com.example.monitorsensors.service;

import com.example.monitorsensors.dto.SensorRequest;
import com.example.monitorsensors.dto.SensorResponse;
import com.example.monitorsensors.dto.SensorStatisticsResponse;
import com.example.monitorsensors.entity.Sensor;
import com.example.monitorsensors.entity.SensorType;
import com.example.monitorsensors.entity.SensorUnit;
import com.example.monitorsensors.mapper.SensorMapper;
import com.example.monitorsensors.repository.SensorRepository;
import com.example.monitorsensors.repository.SensorTypeRepository;
import com.example.monitorsensors.repository.SensorUnitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final SensorTypeRepository typeRepository;
    private final SensorUnitRepository unitRepository;
    private final SensorMapper mapper;

    @Override
    public SensorResponse create(SensorRequest request) {
        log.info("Creating a new sensor with name: {}", request.getName());

        SensorType type = typeRepository.findByName(request.getType())
                .orElseThrow(() -> {
                    log.error("Invalid sensor type: {}", request.getType());
                    return new IllegalArgumentException("Invalid type");
                });

        SensorUnit unit = null;
        if (request.getUnit() != null) {
            unit = unitRepository.findByName(request.getUnit())
                    .orElseThrow(() -> {
                        log.error("Invalid sensor unit: {}", request.getUnit());
                        return new IllegalArgumentException("Invalid unit");
                    });
        }

        Sensor sensor = mapper.toEntity(request, type, unit);
        Sensor savedSensor = sensorRepository.save(sensor);

        log.info("Sensor created successfully with ID: {}", savedSensor.getId());
        return mapper.toDto(savedSensor);
    }

    @Override
    public List<SensorResponse> getAll() {
        log.info("Fetching all sensors");
        List<SensorResponse> sensors = sensorRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        log.info("Fetched {} sensors", sensors.size());
        return sensors;
    }

    @Override
    public List<SensorResponse> search(String name, String model) {
        log.info("Searching for sensors with name: {} and model: {}", name, model);
        List<SensorResponse> sensors = sensorRepository
                .findByNameContainingIgnoreCaseAndModelContainingIgnoreCase(name, model)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
        log.info("Found {} sensors matching the search criteria", sensors.size());
        return sensors;
    }

    @Override
    public SensorResponse update(Long id, SensorRequest request) {
        log.info("Updating sensor with ID: {}", id);

        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Sensor not found with ID: {}", id);
                    return new EntityNotFoundException("Sensor not found");
                });

        SensorType type = typeRepository.findByName(request.getType())
                .orElseThrow(() -> {
                    log.error("Invalid sensor type: {}", request.getType());
                    return new IllegalArgumentException("Invalid type");
                });

        SensorUnit unit = null;
        if (request.getUnit() != null) {
            unit = unitRepository.findByName(request.getUnit())
                    .orElseThrow(() -> {
                        log.error("Invalid sensor unit: {}", request.getUnit());
                        return new IllegalArgumentException("Invalid unit");
                    });
        }

        sensor.setName(request.getName());
        sensor.setModel(request.getModel());
        sensor.setType(type);
        sensor.setUnit(unit);
        sensor.setLocation(request.getLocation());
        sensor.setDescription(request.getDescription());

        sensor.getRange().setFrom(request.getRange().getFrom());
        sensor.getRange().setTo(request.getRange().getTo());

        Sensor updatedSensor = sensorRepository.save(sensor);
        log.info("Sensor with ID: {} updated successfully", updatedSensor.getId());
        return mapper.toDto(updatedSensor);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting sensor with ID: {}", id);
        if (sensorRepository.existsById(id)) {
            sensorRepository.deleteById(id);
            log.info("Sensor with ID: {} deleted successfully", id);
        } else {
            log.error("Sensor with ID: {} not found for deletion", id);
        }
    }

    @Override
    public SensorStatisticsResponse getStatistics() {
        log.info("Fetching sensor statistics");

        long totalSensors = sensorRepository.count();
        log.info("Total number of sensors: {}", totalSensors);

        Map<String, Long> typeCount = sensorRepository.findAll().stream()
                .collect(Collectors.groupingBy(sensor -> sensor.getType().getName(), Collectors.counting()));
        log.info("Sensor type counts: {}", typeCount);

        return new SensorStatisticsResponse(totalSensors, typeCount, LocalDate.now());
    }
}
