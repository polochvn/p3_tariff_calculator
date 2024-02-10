package ru.fastdelivery.domain.common.price;

import ru.fastdelivery.domain.common.currency.Currency;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @param amount   значение цены
 * @param currency валюта цены
 */
public record Price(
        BigDecimal amount,
        Currency currency) {

    private static final double MIN_DISTANCE = 450;

    public Price {
        if (isLessThanZero(amount)) {
            throw new IllegalArgumentException("Price Amount cannot be below Zero!");
        }
    }

    private static boolean isLessThanZero(BigDecimal price) {
        return BigDecimal.ZERO.compareTo(price) > 0;
    }

    public Price multiply(BigDecimal amount) {
        return new Price(this.amount.multiply(amount), this.currency);
    }

    public Price max(Price price) {
        if (!currency.equals(price.currency)) {
            throw new IllegalArgumentException("Cannot compare Prices in difference Currency!");
        }
        return new Price(this.amount.max(price.amount), this.currency);
    }

    public Price calcOnDistance(BigDecimal distance) {
        if (distance.doubleValue() > MIN_DISTANCE) {
            BigDecimal bigDecimal = distance.divide(BigDecimal.valueOf(MIN_DISTANCE), MathContext.DECIMAL128);
            return new Price(bigDecimal.multiply(this.amount).setScale(2, RoundingMode.UP), this.currency);
        }
        return this;
    }
}
