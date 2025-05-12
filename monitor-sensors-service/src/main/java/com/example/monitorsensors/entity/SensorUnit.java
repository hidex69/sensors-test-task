package com.example.monitorsensors.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sensor_unit")
public class SensorUnit {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;
}

