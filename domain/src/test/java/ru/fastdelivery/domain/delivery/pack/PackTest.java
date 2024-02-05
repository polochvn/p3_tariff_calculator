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
    void whenWeightMoreThanMaxWeight_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        var length = new Length(BigInteger.valueOf(150_001));
        var width = new Width(BigInteger.valueOf(150_001));
        var height = new Height(BigInteger.valueOf(150_001));
        assertThatThrownBy(() -> new Pack(weight, length, width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenWeightLessThanMaxWeight_thenObjectCreated() {
        var actual = new Pack(
                new Weight(BigInteger.valueOf(1_000)),
                new Length(BigInteger.valueOf(150_001)),
                new Width(BigInteger.valueOf(150_001)),
                new Height(BigInteger.valueOf(150_001)));
        assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
    }
}