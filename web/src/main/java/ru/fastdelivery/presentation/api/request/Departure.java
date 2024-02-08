package ru.fastdelivery.presentation.api.request;


import java.math.BigDecimal;

public class Departure extends Point {
    public Departure(BigDecimal latitude, BigDecimal longitude) {
        super(latitude, longitude);
    }
}
