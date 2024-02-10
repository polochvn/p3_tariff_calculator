package ru.fastdelivery.domain.common.coordinate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PointFactoryTest {

    CoordinateProvider mockProvider = mock(CoordinateProvider.class);
    PointFactory factory = new PointFactory(mockProvider);

    @Test
    @DisplayName("Координаты пункта отправки NULL и NULL -> исключение")
    void whenLongitudeAndLatitudeIsNull_thenThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.createDeparture(null, null));
    }

    @ParameterizedTest
    @CsvSource({
            "false, true",
            "true, false",
            "false, false"
    })
    @DisplayName("Координаты широты и долготы за пределами диапазона -> исключение")
    void whenLongitudeAndOrLatitudeIsNotAvailable_thenThrowException(boolean isAvailableLatitude, boolean isAvailableLongitude) {
        when(mockProvider.isAvailableLatitude(BigDecimal.valueOf(40))).thenReturn(isAvailableLatitude);
        when(mockProvider.isAvailableLongitude(BigDecimal.valueOf(97))).thenReturn(isAvailableLongitude);

        assertThrows(IllegalArgumentException.class,
                () -> factory.createDestination(BigDecimal.valueOf(40), BigDecimal.valueOf(97)));
    }

    @Test
    @DisplayName("Координаты широты и долготы верные -> новый объект")
    void whenCodeIsAvailable_thenObjectCreated() {
        when(mockProvider.isAvailableLatitude(BigDecimal.valueOf(55))).thenReturn(true);
        when(mockProvider.isAvailableLongitude(BigDecimal.valueOf(75))).thenReturn(true);

        assertThat(factory.createDeparture(BigDecimal.valueOf(55), BigDecimal.valueOf(75))).isNotNull();
    }
}
