package com.example.monitorsensors.service;

import com.example.monitorsensors.dto.SensorRequest;
import com.example.monitorsensors.dto.SensorResponse;
import com.example.monitorsensors.dto.SensorStatisticsResponse;

import java.util.List;

public interface SensorService {
    SensorResponse create(SensorRequest request);

    List<SensorResponse> getAll();

    List<SensorResponse> search(String name, String model);

    SensorResponse update(Long id, SensorRequest request);

    void delete(Long id);

    SensorStatisticsResponse getStatistics();
}
