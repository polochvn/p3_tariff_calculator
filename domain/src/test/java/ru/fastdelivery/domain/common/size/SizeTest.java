package ru.fastdelivery.domain.common.size;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.fastdelivery.domain.common.dimension.Size;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SizeTest {

    @Test
    @DisplayName("Попытка создать отрицательный размер -> исключение")
    void whenSizeBelowZero_thenException() {
        var sizeMillimeters = new BigInteger("-1");
        assertThatThrownBy(() -> new Weight(sizeMillimeters))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void equalsTypeWidth_same() {
        var size = new Weight(new BigInteger("1000"));
        var sizeSame = new Weight(new BigInteger("1000"));

        assertThat(size)
                .isEqualTo(sizeSame)
                .hasSameHashCodeAs(sizeSame);
    }

    @Test
    void equalsNull_false() {
        var size = new Size(new BigInteger("4"));

        assertThat(size).isNotEqualTo(null);
    }

    @ParameterizedTest
    @CsvSource({ "1000, 1, -1",
            "199, 199, 0",
            "50, 999, 1" })
    void compareToTest(BigInteger low, BigInteger high, int expected) {
        var sizeLow = new Size(low);
        var sizeHigh = new Size(high);

        assertThat(sizeLow.compareTo(sizeHigh))
                .isEqualTo(expected);
    }

    static Stream<Arguments> intArrayProvider() {
        return Stream.of(Arguments.of((Object)
                new BigDecimal[]{
                        BigDecimal.valueOf(3),
                        BigDecimal.valueOf(51),
                        BigDecimal.valueOf(112),
                        BigDecimal.valueOf(555),
                        BigDecimal.valueOf(1_045)
                })
        );
    }

    @ParameterizedTest(name = "Размеры = {arguments} -> объект создан")
    @MethodSource("intArrayProvider")
    @DisplayName("Добавление положительного веса -> вес увеличился")
    void whenTestingBothSizeArrays_ShouldBeEqual(BigDecimal[] args) {
        BigDecimal[] arrayOfRoundingSizes = new BigDecimal[args.length];

        for (int i = 0; i < args.length; i++) {
            var roundingSize = new Size(args[i].toBigInteger()).getRoundSizeMillimeters();
            arrayOfRoundingSizes[i] = roundingSize;
        }

        BigDecimal[] expectedSizes = new BigDecimal[]{
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(150),
                BigDecimal.valueOf(600),
                BigDecimal.valueOf(1_050)
        };

        assertThat(expectedSizes).hasSameElementsAs(Arrays.asList(arrayOfRoundingSizes));}

    @Test
    @DisplayName("Первый размер больше второго -> true")
    void whenFirstSizeNotRoundingGreaterThanSecond_thenTrue() {
        var sizeBig = new Size(new BigInteger("599"));
        var sizeSmall = new Size(new BigInteger("597"));

        assertThat(sizeBig.greaterThan(sizeSmall)).isTrue();
    }

    @Test
    @DisplayName("Запрос округленный размер мм -> получено корректное значение")
    void whenGetRound_thenRoundingMillimeters() {
        var size = new Size(new BigInteger("701"));
        var actual = size.getRoundSizeMillimeters();

        assertThat(actual).isEqualByComparingTo(new BigDecimal("750"));
    }
}
