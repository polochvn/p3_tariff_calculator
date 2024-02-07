package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        @Max(value = 1500, message = "Weight should be less than 1500 grams!")
        BigInteger weight,

        @Schema(description = "Длинна упаковки, мм", example = "345.45")
        @Max(value = 1500, message = "Length should be less than 1500 mm!")
        BigInteger length,

        @Schema(description = "Ширина упаковки, мм", example = "589.45")
        @Max(value = 1500, message = "Width should be less than 1500 mm!")
        BigInteger width,

        @Schema(description = "Высота упаковки, мм", example = "234.45")
        @Max(value = 1500, message = "Height should be less than 1500 mm!")
        BigInteger height
) {
}
