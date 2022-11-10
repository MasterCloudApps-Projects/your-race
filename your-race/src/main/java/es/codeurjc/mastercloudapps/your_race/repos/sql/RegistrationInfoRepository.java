package es.codeurjc.mastercloudapps.your_race.repos.sql;

import es.codeurjc.mastercloudapps.your_race.domain.sql.RegistrationInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistrationInfoRepository extends JpaRepository<RegistrationInfo, Long> {
}
