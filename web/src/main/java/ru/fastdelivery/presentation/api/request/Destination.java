package ru.fastdelivery.presentation.api.request;

import java.math.BigDecimal;

public class Destination extends Point {
    public Destination(BigDecimal latitude, BigDecimal longitude) {
        super(latitude, longitude);
    }
}
