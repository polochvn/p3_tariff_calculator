package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Schema(description = "Широта, в градусах", example = "55.45")
    @Min(value = 45, message = "Минимальное значение широты 45 градусов!")
    @Max(value = 65, message = "Максимальное значение широты 65 градусов!")
    BigDecimal latitude;

    @Schema(description = "Долгота, в градусах", example = "65.45")
    @Min(value = 30, message = "Минимальное значение широты 30 градусов!")
    @Max(value = 96, message = "Максимальное значение долготы 96 градусов!")
    BigDecimal longitude;
}
