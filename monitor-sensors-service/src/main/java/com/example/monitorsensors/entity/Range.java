package com.example.monitorsensors.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Range {
    @Column(name = "range_from")
    @Min(0)
    private int from;

    @Column(name = "range_to")
    @Min(0)
    private int to;

    @AssertTrue(message = "'from' must be less than 'to'")
    public boolean isValidRange() {
        return from < to;
    }
}
