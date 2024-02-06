package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.dimension.Height;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.dimension.Width;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    void whenPackMoreThanMaxCharacteristics_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        var length = new Length(BigInteger.valueOf(1_501));
        var width = new Width(BigInteger.valueOf(1_501));
        var height = new Height(BigInteger.valueOf(1_501));
        assertThatThrownBy(() -> new Pack(weight, length, width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenPackageLessThanMaxPackParams_thenObjectCreated() {
        var actual = new Pack(
                new Weight(BigInteger.valueOf(1_000)),
                new Length(BigInteger.valueOf(1_100)),
                new Width(BigInteger.valueOf(750)),
                new Height(BigInteger.valueOf(350)));

        assertThat(actual).isEqualTo(new Pack(
                new Weight(BigInteger.valueOf(1_000)),
                new Length(BigInteger.valueOf(1_100)),
                new Width(BigInteger.valueOf(750)),
                new Height(BigInteger.valueOf(350))));

    }
}