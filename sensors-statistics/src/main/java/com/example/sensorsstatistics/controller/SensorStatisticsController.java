package com.example.sensorsstatistics.controller;


import com.example.sensorsstatistics.entity.SensorStatistics;
import com.example.sensorsstatistics.service.SensorStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class SensorStatisticsController {

    private final SensorStatisticsService statisticsService;

    @GetMapping
    public List<SensorStatistics> getStatisticsByDateRange(@RequestParam LocalDate startDate,
                                                           @RequestParam LocalDate endDate) {
        return statisticsService.getStatisticsByDateRange(startDate, endDate);
    }

    @PostMapping("/test-job")
    public void testJob() {
        statisticsService.collectAndSaveStatistics();
    }
}