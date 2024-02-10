package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coordinate.CoordinateProvider;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;
import ru.fastdelivery.domain.common.coordinate.PointFactory;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.Height;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.dimension.Width;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TariffCalculateUseCaseTest {

    final PriceProvider priceProvider = mock(PriceProvider.class);
    final CoordinateProvider coordinateProvider = mock(CoordinateProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");
    final BigDecimal latitudeDest = new BigDecimal("73.398660");
    final BigDecimal longitudeDest = new BigDecimal("55.027532");
    final BigDecimal latitudeDep = new BigDecimal("55.446008");
    final BigDecimal longitudeDep = new BigDecimal("65.339151");
    final TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(priceProvider);

    @BeforeEach
    void mockedParams(){
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var priceCubMeter = new Price(BigDecimal.valueOf(300), currency);

        when(priceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(priceProvider.costPerKg()).thenReturn(pricePerKg);
        when(priceProvider.costCubicMeters()).thenReturn(priceCubMeter);
        when(coordinateProvider.isAvailableLatitude(latitudeDest)).thenReturn(true);
        when(coordinateProvider.isAvailableLongitude(longitudeDest)).thenReturn(true);
        when(coordinateProvider.isAvailableLatitude(latitudeDep)).thenReturn(true);
        when(coordinateProvider.isAvailableLongitude(longitudeDep)).thenReturn(true);
    }

    @Test
    @DisplayName("Расчет стоимости доставки -> успешно")
    void whenCalculatePrice_thenSuccess() {
        Destination destination = new PointFactory(coordinateProvider).createDestination(latitudeDest, longitudeDest);
        Departure departure = new PointFactory(coordinateProvider).createDeparture(latitudeDep, longitudeDep);

        var shipment = new Shipment(List.of(
                new Pack(new Weight(BigInteger.valueOf(1200)), new Length(BigInteger.valueOf(1_341)),
                        new Width(BigInteger.valueOf(431)), new Height(BigInteger.valueOf(1_021)))),
                new CurrencyFactory(code -> true).create("RUB"), destination, departure);

        var expectedPrice = new Price(BigDecimal.valueOf(874.35), currency);
        var actualPrice = tariffCalculateUseCase.calc(shipment);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {
        BigDecimal minimalValue = BigDecimal.TEN;
        var minimalPrice = new Price(minimalValue, currency);
        when(priceProvider.minimalPrice()).thenReturn(minimalPrice);

        var actual = tariffCalculateUseCase.minimalPrice();

        assertThat(actual).isEqualTo(minimalPrice);
    }
}