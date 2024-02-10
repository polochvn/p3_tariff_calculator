package ru.fastdelivery.domain.common.coordinate;

import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class PointFactory {

    private final CoordinateProvider coordinateProvider;

    public Destination createDestination(BigDecimal latitude, BigDecimal longitude) {
        checkPoint(latitude, longitude);
        return new Destination(latitude, longitude);
    }

    public Departure createDeparture(BigDecimal latitude, BigDecimal longitude) {
        checkPoint(latitude, longitude);
        return new Departure(latitude, longitude);
    }

    public void checkPoint(BigDecimal latitude, BigDecimal longitude) {
        if (!coordinateProvider.isAvailableLatitude(latitude) || !coordinateProvider.isAvailableLongitude(longitude)) {
            throw new IllegalArgumentException("Coordinates of point is unacceptable");
        }
    }
}
