package ru.fastdelivery.properties.provider;


import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.coordinate.CoordinateProvider;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@ConfigurationProperties("point.degree")
@Setter
public class CoordinateProperties implements CoordinateProvider {

    List<BigDecimal> latitudes;
    List<BigDecimal> longitudes;
}
