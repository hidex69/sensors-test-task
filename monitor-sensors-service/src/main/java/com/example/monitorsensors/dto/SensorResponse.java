package com.example.monitorsensors.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ответ на запрос датчика")
public class SensorResponse {

    @Schema(description = "Идентификатор датчика", example = "1")
    private Long id;

    @Schema(description = "Название датчика", example = "Thermometer")
    private String name;

    @Schema(description = "Модель датчика", example = "t-1000")
    private String model;

    @Schema(description = "Диапазон измерений датчика", implementation = RangeDto.class)
    private RangeDto range;

    @Schema(description = "Тип датчика", example = "Temperature")
    private String type;

    @Schema(description = "Единица измерения", example = "°C")
    private String unit;

    @Schema(description = "Местоположение датчика", example = "Kitchen")
    private String location;

    @Schema(description = "Описание датчика", example = "Digital temperature sensor")
    private String description;
}
