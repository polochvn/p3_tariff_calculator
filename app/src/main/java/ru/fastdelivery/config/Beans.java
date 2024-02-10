package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.coordinate.PointFactory;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.properties.provider.CoordinateProperties;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.PriceProvider;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class Beans {

    @Bean
    public PointFactory pointFactory(CoordinateProperties coordinateProperties) {
        return new PointFactory(coordinateProperties);
    }

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(PriceProvider priceProvider) {
        return new TariffCalculateUseCase(priceProvider);
    }
}
