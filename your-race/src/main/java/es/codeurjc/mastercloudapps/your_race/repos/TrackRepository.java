package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TrackRepository extends JpaRepository<Track, Long> {
    Optional<Track> findByAthleteAndRace(Athlete athlete, Race race);
}
