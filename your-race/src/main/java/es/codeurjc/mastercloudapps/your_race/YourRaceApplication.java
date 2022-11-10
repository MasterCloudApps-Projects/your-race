package es.codeurjc.mastercloudapps.your_race;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.spi.FeatureProvider;


@SpringBootApplication
public class YourRaceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(YourRaceApplication.class, args);
    }

    /*
    @Bean
    public FeatureProvider featureProvider() {
        return new EnumBasedFeatureProvider(Features.class);
    }*/

}
