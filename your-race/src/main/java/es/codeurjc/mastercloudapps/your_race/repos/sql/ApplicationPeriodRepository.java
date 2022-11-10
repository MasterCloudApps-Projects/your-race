package es.codeurjc.mastercloudapps.your_race.repos.sql;

import es.codeurjc.mastercloudapps.your_race.domain.sql.ApplicationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApplicationPeriodRepository extends JpaRepository<ApplicationPeriod, Long> {
}
