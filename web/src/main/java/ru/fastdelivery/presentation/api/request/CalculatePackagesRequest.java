package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(

        @NotEmpty
        @NotNull
        @Schema(description = "Список упаковок отправления",
                example =
                        "[{\"weight\": 4564,\n" +
                                "  \"length\": 345,\n" +
                                "  \"width\": 589,\n" +
                                "  \"height\": 234]")
        List<@Valid CargoPackage> packages,

        @NotNull
        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        String currencyCode,

        @Valid
        @NotNull
        @Schema(description = "Пункт назначения товара",
                example =
                        "{\"latitude\" : 73.398660,\n" +
                                " \"longitude\" : 55.027532}")
        Destination destination,

        @Valid
        @NotNull
        @Schema(description = "Пункт отправки товари",
                example =
                        "{\"latitude\" : 55.446008,\n" +
                                " \"longitude\" : 65.339151}")
        Departure departure
) {
}
