package com.example.monitorsensors.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String model;

    @Embedded
    private Range range;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private SensorType type;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private SensorUnit unit;

    @Size(max = 40)
    private String location;

    @Size(max = 200)
    private String description;
}

