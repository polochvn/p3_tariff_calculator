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

    @Override
    public boolean isAvailableLatitude(BigDecimal latitude) {
        return latitude.doubleValue() >= latitudes.get(1).doubleValue() && latitude.doubleValue() <= latitudes.get(2).doubleValue();
    }

    @Override
    public boolean isAvailableLongitude(BigDecimal longitude) {
        return longitude.doubleValue() >= longitudes.get(1).doubleValue() && longitude.doubleValue() <= longitudes.get(2).doubleValue();
    }
}
