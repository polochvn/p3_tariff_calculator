package ru.fastdelivery.calc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.domain.common.coordinate.PointFactory;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.Departure;
import ru.fastdelivery.presentation.api.request.Destination;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CalculateControllerTest extends ControllerTest {

    final String baseCalculateApi = "/api/v1/calculate/";
    @MockBean
    TariffCalculateUseCase useCase;
    @MockBean
    CurrencyFactory currencyFactory;
    @MockBean
    PointFactory pointFactory;

    @Test
    @DisplayName("Валидные данные для расчета стоимости -> Ответ 200")
    void whenValidInputData_thenReturn200() {
        var request = new CalculatePackagesRequest(List.of(
                new CargoPackage(BigInteger.valueOf(1_468), BigInteger.valueOf(1_423),
                        BigInteger.valueOf(323), BigInteger.valueOf(723))),
                "RUB",
                new Destination(BigDecimal.valueOf(62.353555), BigDecimal.valueOf(95.535355)),
                new Departure(BigDecimal.valueOf(64.009354), BigDecimal.valueOf(77.000034)));
        var rub = new CurrencyFactory(code -> true).create("RUB");

        when(useCase.calc(any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
        when(useCase.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));

        ResponseEntity<CalculatePackagesResponse> response =
                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Список упаковок == null -> Ответ 400")
    void whenEmptyListPackages_thenReturn400() {
        var request = new CalculatePackagesRequest(null, "RUB",
                new Destination(BigDecimal.valueOf(49.1539), BigDecimal.valueOf(54.398)),
                new Departure(BigDecimal.valueOf(64.1804), BigDecimal.valueOf(95.55)));

        ResponseEntity<String> response = restTemplate.postForEntity(baseCalculateApi, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 1, -1, 200",
            "1999, 199, 0, 435",
            "1500, 2943, 1, 435",
            "451, 242, 431, 1501"})
    @DisplayName("Невалидные данные для расчета стоимость -> Ответ 400 BAD_REQUEST")
    void whenNotValidInputParamsOfPackageData_thenReturn400(BigInteger weight, BigInteger length, BigInteger width, BigInteger height) {
        var request = new CalculatePackagesRequest(
                List.of(new CargoPackage(weight, length, width, height)), "RUB",
                new Destination(BigDecimal.valueOf(49.1539), BigDecimal.valueOf(54.398)),
                new Departure(BigDecimal.valueOf(64.1804), BigDecimal.valueOf(95.55)));
        var rub = new CurrencyFactory(code -> true).create("RUB");
        when(useCase.calc(any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
        when(useCase.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));

        ResponseEntity<CalculatePackagesResponse> response =
                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource({
            "44.444623, 31.537625, 55.353521, 95.245235",
            "45.199935, 96.353199, 31.035355, 77.435434",
            "21.150054, 32.294354, 105.154085, -32.435242",
            "-43.451896, 0.242757, 88.431252, 32.150125"})
    @DisplayName("Невалидные данные для расчета стоимость -> Ответ 400 BAD_REQUEST")
    void whenNotValidInputGeodesicPoint_thenReturn400(BigDecimal destLatitude, BigDecimal destLongitude, BigDecimal depLatitude, BigDecimal depLongitude) {
        var request = new CalculatePackagesRequest(
                List.of(new CargoPackage(BigInteger.valueOf(1_468), BigInteger.valueOf(1_423),
                        BigInteger.valueOf(323), BigInteger.valueOf(723))), "RUB",
                new Destination(destLatitude, destLongitude), new Departure(depLatitude, depLongitude));
        var rub = new CurrencyFactory(code -> true).create("RUB");
        when(useCase.calc(any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
        when(useCase.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));

        ResponseEntity<CalculatePackagesResponse> response =
                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
