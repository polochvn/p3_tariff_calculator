package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Point {

    @Schema(description = "Ширина упаковки, мм", example = "589.45")
    @Min(value = 45, message = "")
    @Max(value = 65, message = "Width should be less than 1500 mm!")
    BigDecimal latitude;

    @Schema(description = "Высота упаковки, мм", example = "234.45")
    @Min(value = 30, message = "")
    @Max(value = 96, message = "Height should be less than 1500 mm!")
    BigDecimal longitude;
}
