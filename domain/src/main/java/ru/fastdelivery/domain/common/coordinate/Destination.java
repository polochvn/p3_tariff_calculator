package ru.fastdelivery.domain.common.coordinate;


import java.math.BigDecimal;

public class Destination extends Point {
    public Destination(BigDecimal latitude, BigDecimal longitude) {
        super(latitude, longitude);
    }
}
