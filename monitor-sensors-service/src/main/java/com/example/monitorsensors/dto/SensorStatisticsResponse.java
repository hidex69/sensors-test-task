package com.example.monitorsensors.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
public class SensorStatisticsResponse {

    @Schema(description = "Общее количество датчиков", example = "100")
    private Long totalSensors;

    @Schema(description = "Ассоциативный массив типов датчиков и их количества",
            example = "{'Temperature': 50, 'Pressure': 30, 'Humidity': 20}")
    private Map<String, Long> typeCount;

    @Schema(description = "Дата получения статистики", example = "2025-12-01")
    private LocalDate date;
}
