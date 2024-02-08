package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.common.price.Price;

public interface PriceProvider {
    Price costPerKg();

    Price minimalPrice();

    Price costCubicMeters();
}
