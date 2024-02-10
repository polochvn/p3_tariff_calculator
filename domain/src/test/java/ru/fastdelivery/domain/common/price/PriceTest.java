package ru.fastdelivery.domain.common.price;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    final Currency currency = new CurrencyFactory(code -> true).create("RUB");


    @Test
    void whenAmountBelowZero_thenException() {
        var amount = BigDecimal.valueOf(-1);
        assertThatThrownBy(() -> new Price(amount, currency))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void multiply() {
        var price = new Price(BigDecimal.valueOf(10), currency);
        var pieces = new BigDecimal("2.54");
        var expected = new Price(BigDecimal.valueOf(25.4), currency);

        var actualPrice = price.multiply(pieces);

        assertThat(actualPrice.amount()).isEqualByComparingTo(expected.amount());
        assertThat(actualPrice.currency()).isEqualTo(expected.currency());
    }

    @Test
    void max() {
        var price = new Price(BigDecimal.valueOf(10), currency);
        var moreThanPrice = new Price(BigDecimal.valueOf(100), currency);

        var actualMax = price.max(moreThanPrice);

        assertThat(actualMax).isEqualTo(moreThanPrice);
    }

    @Test
    void maxWithDifferentCurrency_thenException() {
        var price = new Price(BigDecimal.valueOf(10), currency);
        var moreThanPrice = new Price(BigDecimal.valueOf(100),
                new CurrencyFactory(code -> true).create("USD"));

        assertThatThrownBy(() -> price.max(moreThanPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void calcPriceWhenDistanceMoreThanMinDistance() {
        var price = new Price(BigDecimal.valueOf(345), currency);
        var moreThanMinDistance = new BigDecimal(500);

        var actualPrice = price.calcOnDistance(moreThanMinDistance);
        var expectedPrice = new Price(BigDecimal.valueOf(383.34), currency);

        assertThat(actualPrice).isEqualTo(expectedPrice);
    }

    @Test
    void calcPriceWhenDistanceLessThanMinDistance() {
        var price = new Price(BigDecimal.valueOf(788.23), currency);
        var lessThanMinDistance = new BigDecimal(200);

        var actualPrice = price.calcOnDistance(lessThanMinDistance);

        assertThat(actualPrice).isEqualTo(price);
    }
}