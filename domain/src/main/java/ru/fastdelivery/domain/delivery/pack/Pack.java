package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.dimension.Height;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.dimension.Size;
import ru.fastdelivery.domain.common.dimension.Width;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
public record Pack(Weight weight, Length length, Width width, Height height) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));
    private static final Size maxSize = new Size(BigInteger.valueOf(1_500));

    public Pack {
        if (weight.greaterThan(maxWeight) && length.greaterThan(maxSize) &&
                width.greaterThan(maxSize) && height.greaterThan(maxSize)) {
            throw new IllegalArgumentException("Package can't be more than this characteristics: size " +
                    maxSize + ". weight " + maxWeight);
        }
    }

    public BigDecimal volumeCubicMeters() {
        BigDecimal volumeCubicMillimeters = length.getRoundSizeMillimeters()
                .multiply(width.getRoundSizeMillimeters())
                .multiply(height.getRoundSizeMillimeters());

        BigDecimal volumeCubicMeters = volumeCubicMillimeters.divide(BigDecimal.valueOf(1_000_000_000));
        DecimalFormat df = new DecimalFormat(".####");

        return new BigDecimal(df.format(volumeCubicMeters).replace(",", "."));
    }
}
