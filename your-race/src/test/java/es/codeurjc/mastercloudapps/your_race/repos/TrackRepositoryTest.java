package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("postgres")
class TrackRepositoryTest {

    @Autowired 
    private TrackRepository trackRepository;
    @Autowired
    private AthleteRepository athleteRepository;
    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private OrganizerRepository organizerRepository;
    
    @Test
    void findByAthleteIdAndRaceId() {
        Organizer organizer = organizerRepository.save(Organizer.builder().name("Pepe").build());
        Athlete athlete = athleteRepository.save(Athlete.builder().name("Pepe").build());
        Race race = raceRepository.save(Race.builder().name("Carrera 1").location("Localización").organizer(organizer).build());
        Track track = trackRepository.save(Track.builder()
                .athlete(athlete)
                .race(race)
                .build());
        Optional<Track> findTrack = trackRepository.findByAthleteAndRace(track.getAthlete(),track.getRace());
        assertEquals(track.getId(),findTrack.get().getId());
    }

}