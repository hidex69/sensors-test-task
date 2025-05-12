package com.example.sensorsstatistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
public class SensorStatisticsResponse {

    private Long totalSensors;

    private Map<String, Long> typeCount;

    private LocalDate date;
}
