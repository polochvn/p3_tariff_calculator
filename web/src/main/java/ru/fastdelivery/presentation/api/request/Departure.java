package ru.fastdelivery.presentation.api.request;


import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class Departure extends Point {
    public Departure(BigDecimal latitude, BigDecimal longitude) {
        super(latitude, longitude);
    }
}
