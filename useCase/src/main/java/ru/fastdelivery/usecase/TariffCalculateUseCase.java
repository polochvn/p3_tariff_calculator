package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final PriceProvider priceProvider;

    public Price calc(Shipment shipment) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var minimalPrice = priceProvider.minimalPrice();
        var priceAllPackagesByVolume = priceProvider.costCubicMeters().multiply(shipment.volumeAllPackages());

        return priceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalPrice)
                .max(priceAllPackagesByVolume);
    }

    public Price minimalPrice() {
        return priceProvider.minimalPrice();
    }

    public BigDecimal calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return BigDecimal.valueOf(6371).multiply(BigDecimal.valueOf(c));
    }
}
