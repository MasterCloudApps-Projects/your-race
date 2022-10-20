package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.RegistrationInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistrationRepository extends JpaRepository<RegistrationInfo, Long> {
}
