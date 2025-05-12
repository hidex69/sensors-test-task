package com.example.monitorsensors.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SensorRequest {

    @Schema(description = "Название датчика", example = "Thermometer", minLength = 3, maxLength = 30)
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @Schema(description = "Модель датчика", example = "t-1000", maxLength = 15)
    @NotBlank
    @Size(max = 15)
    private String model;

    @Schema(description = "Диапазон измерений")
    @Valid
    @NotNull
    private RangeDto range;

    @Schema(description = "Тип датчика", example = "Temperature")
    @NotBlank
    private String type;

    @Schema(description = "Единица измерения", example = "°С")
    private String unit;

    @Schema(description = "Местоположение", example = "Kitchen", maxLength = 40)
    @Size(max = 40)
    private String location;

    @Schema(description = "Описание", example = "Digital temperature sensor", maxLength = 200)
    @Size(max = 200)
    private String description;
}

