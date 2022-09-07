package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
