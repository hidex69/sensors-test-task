package com.example.monitorsensors.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RangeDto {

    @Schema(description = "Минимальное значение диапазона", example = "10")
    @Min(0)
    private int from;

    @Schema(description = "Максимальное значение диапазона", example = "100")
    @Min(1)
    private int to;
}


