package ru.fastdelivery.domain.common.dimension;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Size implements Comparable<Size> {

    private final BigInteger sizeMillimeters;

    public Size(BigInteger sizeMillimeters) {
        if (isLessThanZero(sizeMillimeters)) {
            throw new IllegalArgumentException("Size cannot be below Zero!");
        }
        this.sizeMillimeters = sizeMillimeters;
    }

    private static boolean isLessThanZero(BigInteger size) {
        return BigInteger.ZERO.compareTo(size) > 0;
    }

    public BigInteger getSizeMillimeters() {
        return sizeMillimeters;
    }

    public BigDecimal getRoundSizeMillimeters() {
        return new BigDecimal(String.valueOf(50 * ((int) Math.ceil((double) sizeMillimeters.intValue() / 50))));
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Size size = (Size) o;
        return sizeMillimeters.compareTo(size.sizeMillimeters) == 0;
    }

    @Override
    public int compareTo(Size s) {
        return s.getSizeMillimeters().compareTo(getSizeMillimeters());
    }

    public boolean greaterThan(Size s) {
        return sizeMillimeters.compareTo(s.sizeMillimeters) > 0;
    }
}
