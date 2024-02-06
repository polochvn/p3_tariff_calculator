package ru.fastdelivery.domain.common.size;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.fastdelivery.domain.common.dimension.Size;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SizeFactoryTest {

    @ParameterizedTest(name = "Размер = {arguments} -> объект создан")
    @ValueSource(longs = { 0, 37, 112, 555, 1_045})
    void whenMillimetersGreaterThanZeroWithRounding_thenObjectCreated(long amount) {
        var size = new Size(BigInteger.valueOf(amount));

        assertNotNull(size);
        assertThat(size.getSizeMillimeters()).isEqualByComparingTo(BigInteger.valueOf(amount));
    }

    @ParameterizedTest(name = "Размер = {arguments} -> исключение")
    @ValueSource(longs = { -1, -100, -10_000 })
    @DisplayName("Значение размера ниже 0.00 -> исключение")
    void whenMillimetersLessThanZero_thenThrowException(long amount) {
        assertThatThrownBy(() -> new Size(BigInteger.valueOf(amount)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
