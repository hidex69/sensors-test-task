package com.example.monitorsensors.controller;

import com.example.monitorsensors.dto.SensorRequest;
import com.example.monitorsensors.dto.SensorResponse;
import com.example.monitorsensors.dto.SensorStatisticsResponse;
import com.example.monitorsensors.service.SensorService;
import com.example.monitorsensors.service.SensorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
@Tag(name = "Sensors", description = "Управление датчиками мониторинга")
public class SensorController {

    private final SensorService service;

    @Operation(summary = "Получить все датчики", description = "Доступно для ролей Viewer и Administrator")
    @ApiResponse(responseCode = "200", description = "Список датчиков получен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SensorResponse.class)))
    @PreAuthorize("hasAnyRole('VIEWER', 'ADMINISTRATOR')")
    @GetMapping
    public List<SensorResponse> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Поиск датчиков", description = "Поиск по полям name и model (частичное совпадение). Доступно Viewer и Administrator.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результаты поиска",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SensorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('VIEWER', 'ADMINISTRATOR')")
    @GetMapping("/search")
    public List<SensorResponse> search(
            @Parameter(description = "Часть названия датчика") @RequestParam(defaultValue = "") String name,
            @Parameter(description = "Часть модели датчика") @RequestParam(defaultValue = "") String model
    ) {
        return service.search(name, model);
    }

    @Operation(summary = "Создать новый датчик", description = "Доступно только роли Administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Датчик создан",
                    content = @Content(schema = @Schema(implementation = SensorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<SensorResponse> create(
            @Parameter(description = "Данные нового датчика") @Valid @RequestBody SensorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "Обновить датчик", description = "Доступно только роли Administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Датчик обновлён",
                    content = @Content(schema = @Schema(implementation = SensorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Датчик не найден")
    })
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}")
    public SensorResponse update(
            @Parameter(description = "ID датчика") @PathVariable Long id,
            @Parameter(description = "Обновлённые данные") @Valid @RequestBody SensorRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "Удалить датчик", description = "Доступно только роли Administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Датчик удалён"),
            @ApiResponse(responseCode = "404", description = "Датчик не найден")
    })
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID датчика") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Статистика датчиков", description = "Получение статистики по датчикам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результаты получения статистики",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SensorStatisticsResponse.class)))
    })
    @GetMapping("/statistics")
    public SensorStatisticsResponse statistics() {
        return service.getStatistics();
    }
}
