package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;

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
                .max(priceAllPackagesByVolume)
                .calcOnDistance(shipment.calculateDistance());
    }

    public Price minimalPrice() {
        return priceProvider.minimalPrice();
    }
}
