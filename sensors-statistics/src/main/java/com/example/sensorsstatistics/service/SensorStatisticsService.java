package com.example.sensorsstatistics.service;

import com.example.sensorsstatistics.dto.SensorStatisticsResponse;
import com.example.sensorsstatistics.entity.SensorStatistics;
import com.example.sensorsstatistics.entity.SensorStatisticsTypeCount;
import com.example.sensorsstatistics.repository.SensorStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorStatisticsService {

    private final SensorStatisticsRepository statisticsRepository;
    private final RestTemplate restTemplate;

    @Value("${sensor.api.url:http://localhost:8080/sensors/statistics}")
    private String SENSOR_API_URL;

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY = 5000L;

    public SensorStatisticsResponse fetchStatistics() {
        log.info("Fetching statistics from the monitor sensors API...");
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                SensorStatisticsResponse statistics = restTemplate.getForObject(SENSOR_API_URL, SensorStatisticsResponse.class);
                log.info("Successfully fetched statistics: {}", statistics);
                return statistics;
            } catch (HttpStatusCodeException e) {
                log.error("HTTP error while fetching statistics: {}", e.getMessage(), e);
            } catch (ResourceAccessException e) {
                log.error("Error connecting to the sensor statistics service: {}", e.getMessage(), e);
            } catch (Exception e) {
                log.error("Unknown error while fetching statistics: {}", e.getMessage(), e);
            }

            attempts++;
            log.warn("Retrying to fetch statistics (attempt {}/{})...", attempts, MAX_RETRIES);
            try {
                Thread.sleep(RETRY_DELAY);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        throw new RuntimeException("Failed to fetch statistics after " + MAX_RETRIES + " attempts.");
    }

    public void saveStatistics(SensorStatisticsResponse statisticsResponse) {
        log.info("Saving statistics to the database: {}", statisticsResponse);
        SensorStatistics statistics = new SensorStatistics();
        statistics.setTotalSensors(statisticsResponse.getTotalSensors());
        List<SensorStatisticsTypeCount> typeCounts = statisticsResponse.getTypeCount().entrySet().stream()
                .map(entry -> new SensorStatisticsTypeCount(entry.getKey(), entry.getValue()))
                .toList();
        statistics.setTypeCount(typeCounts);
        statistics.setDate(statisticsResponse.getDate());
        statisticsRepository.save(statistics);
        log.info("Statistics saved successfully for date: {}", statisticsResponse.getDate());
    }

    @Scheduled(cron = "0 0 2 * * ?") // Каждый день в 02:00
    public void collectAndSaveStatistics() {
        log.info("Scheduled task started to collect and save statistics...");
        try {
            SensorStatisticsResponse statisticsResponse = fetchStatistics();
            saveStatistics(statisticsResponse);
            log.info("Statistics collection and saving completed successfully.");
        } catch (Exception e) {
            log.error("Error during scheduled statistics collection: {}", e.getMessage(), e);
        }
    }

    public List<SensorStatistics> getStatisticsByDateRange(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching statistics from the database between {} and {}", startDate, endDate);
        List<SensorStatistics> statistics = statisticsRepository.findByDateBetween(startDate, endDate);
        log.info("Found {} records in the database for the given date range.", statistics.size());
        return statistics;
    }
}
