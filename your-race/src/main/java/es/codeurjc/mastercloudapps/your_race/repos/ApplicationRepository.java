package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.sql.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByApplicationCode(String applicationCode);
}
