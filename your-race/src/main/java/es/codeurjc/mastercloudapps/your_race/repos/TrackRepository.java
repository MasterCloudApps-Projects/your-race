package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.sql.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Race;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TrackRepository extends JpaRepository<Track, Long> {
    Optional<Track> findByAthleteAndRace(Athlete athlete, Race race);
    int countByRace(Race race);
}
