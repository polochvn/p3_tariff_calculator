package ru.fastdelivery.domain.common.dimension;

import java.math.BigInteger;

public class Size implements Comparable<Size> {

    private final BigInteger sizeMillimeters;

    public Size(BigInteger sizeMillimeters) {
        if (isLessThanZero(sizeMillimeters)) {
            throw new IllegalArgumentException("Size cannot be below Zero!");
        }
        if (isGreaterThanMaxSize(sizeMillimeters)) {
            throw new IllegalArgumentException("Size cannot be larger than 1,500 mm!");
        }
        this.sizeMillimeters = sizeMillimeters;
    }

    private static boolean isLessThanZero(BigInteger size) {
        return BigInteger.ZERO.compareTo(size) > 0;
    }

    private static boolean isGreaterThanMaxSize(BigInteger size) {
        return BigInteger.valueOf(1_500).compareTo(size) < 0;
    }

    public BigInteger getSizeMillimeters() {
        return new BigInteger(String.valueOf(50 * ((double) Math.abs(sizeMillimeters.intValue() / 50))));
    }

    @Override
    public int compareTo(Size s) {
        return s.getSizeMillimeters().compareTo(getSizeMillimeters());
    }
}
