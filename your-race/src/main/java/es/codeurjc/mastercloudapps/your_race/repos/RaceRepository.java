package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.Race;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RaceRepository extends JpaRepository<Race, Long> {
}
