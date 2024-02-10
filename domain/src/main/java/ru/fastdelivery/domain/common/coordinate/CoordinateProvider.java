package ru.fastdelivery.domain.common.coordinate;

import java.math.BigDecimal;

public interface CoordinateProvider {

    boolean isAvailableLatitude(BigDecimal latitude);
    boolean isAvailableLongitude(BigDecimal longitude);
}
