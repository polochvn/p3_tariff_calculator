package ru.fastdelivery.presentation.api.request;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class Destination extends Point {
    public Destination(BigDecimal latitude, BigDecimal longitude) {
        super(latitude, longitude);
    }
}
