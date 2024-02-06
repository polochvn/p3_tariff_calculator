package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.Height;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.dimension.Width;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    @Test
    void whenSummarizingWeightAndVolumeOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var length1 = new Length(BigInteger.valueOf(1_432));
        var width1 = new Width(BigInteger.valueOf(1_234));
        var height1 = new Height(BigInteger.valueOf(712));

        var weight2 = new Weight(BigInteger.ONE);
        var length2 = new Length(BigInteger.valueOf(1_343));
        var width2 = new Width(BigInteger.valueOf(443));
        var height2 = new Height(BigInteger.valueOf(1_323));

        var packages = List.of(new Pack(weight1, length1, width1, height1), new Pack(weight2, length2, width2, height2));
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));

        var massOfShipment = shipment.weightAllPackages();
        var volumeOfShipment = shipment.volumeAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
        assertThat(volumeOfShipment).isEqualByComparingTo(BigDecimal.valueOf(2.1795));
    }
}