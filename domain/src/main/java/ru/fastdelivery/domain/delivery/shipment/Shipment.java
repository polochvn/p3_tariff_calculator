package ru.fastdelivery.domain.delivery.shipment;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency,
        Destination destination,
        Departure departure
) {

    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }

    public BigDecimal volumeAllPackages() {
        return packages.stream()
                .map(Pack::volumeCubicMeters)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateDistance() {
        Geodesic geodesic = Geodesic.WGS84;
        GeodesicData result = geodesic.Inverse(
                departure.getDoubleLatitude(),
                departure.getDoubleLongitude(),
                destination.getDoubleLatitude(),
                destination.getDoubleLongitude());
        return BigDecimal.valueOf(result.s12 / 1000).setScale(0, RoundingMode.UP);
    }
}
