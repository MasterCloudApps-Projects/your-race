package es.codeurjc.mastercloudapps.your_race.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("es.codeurjc.mastercloudapps.your_race.domain")
@EnableJpaRepositories("es.codeurjc.mastercloudapps.your_race.repos")
@EnableTransactionManagement
public class DomainConfig {
}
