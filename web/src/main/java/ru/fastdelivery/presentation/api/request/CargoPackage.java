package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,

        @Schema(description = "Длинна упаковки, мм", example = "345.45")
        BigInteger length,

        @Schema(description = "Ширина упаковки, мм", example = "589.45")
        BigInteger width,

        @Schema(description = "Высота упаковки, мм", example = "234.45")
        BigInteger height
) {
}
