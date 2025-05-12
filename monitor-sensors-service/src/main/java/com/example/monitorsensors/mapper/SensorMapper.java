package com.example.monitorsensors.mapper;

import com.example.monitorsensors.dto.RangeDto;
import com.example.monitorsensors.dto.SensorRequest;
import com.example.monitorsensors.dto.SensorResponse;
import com.example.monitorsensors.entity.Range;
import com.example.monitorsensors.entity.Sensor;
import com.example.monitorsensors.entity.SensorType;
import com.example.monitorsensors.entity.SensorUnit;
import org.springframework.stereotype.Component;

@Component
public class SensorMapper {

    public Sensor toEntity(SensorRequest dto, SensorType type, SensorUnit unit) {
        Sensor sensor = new Sensor();
        sensor.setName(dto.getName());
        sensor.setModel(dto.getModel());
        sensor.setType(type);
        sensor.setUnit(unit);
        sensor.setLocation(dto.getLocation());
        sensor.setDescription(dto.getDescription());

        Range range = new Range();
        range.setFrom(dto.getRange().getFrom());
        range.setTo(dto.getRange().getTo());
        sensor.setRange(range);

        return sensor;
    }

    public SensorResponse toDto(Sensor entity) {
        SensorResponse dto = new SensorResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setModel(entity.getModel());
        dto.setType(entity.getType().getName());
        dto.setUnit(entity.getUnit() != null ? entity.getUnit().getName() : null);
        dto.setLocation(entity.getLocation());
        dto.setDescription(entity.getDescription());

        RangeDto rangeDto = new RangeDto();
        rangeDto.setFrom(entity.getRange().getFrom());
        rangeDto.setTo(entity.getRange().getTo());
        dto.setRange(rangeDto);

        return dto;
    }
}

