package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TrackRepository extends JpaRepository<Track, Long> {
}
