package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.RegistrationInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistrationInfoRepository extends JpaRepository<RegistrationInfo, Long> {
}
