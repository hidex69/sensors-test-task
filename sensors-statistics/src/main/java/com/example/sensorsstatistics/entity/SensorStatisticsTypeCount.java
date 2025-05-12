package com.example.sensorsstatistics.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class SensorStatisticsTypeCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sensorType;

    private Long sensorCount;

    @ManyToOne
    @JoinColumn(name = "sensor_statistics_id", nullable = false)
    @JsonIgnore
    private SensorStatistics sensorStatistics;

    public SensorStatisticsTypeCount(String sensorType, Long sensorCount) {
        this.sensorType = sensorType;
        this.sensorCount = sensorCount;
    }
}
