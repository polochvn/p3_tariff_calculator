package ru.fastdelivery.domain.common.coordinate;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Point {

    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public double getDoubleLatitude() {
        return latitude.doubleValue();
    }

    public double getDoubleLongitude() {
        return longitude.doubleValue();
    }
}
