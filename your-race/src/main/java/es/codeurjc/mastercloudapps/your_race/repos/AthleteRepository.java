package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.sql.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
