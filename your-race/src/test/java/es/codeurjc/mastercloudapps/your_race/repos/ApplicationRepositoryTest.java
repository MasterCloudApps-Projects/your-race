package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("postgres")
class ApplicationRepositoryTest {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private AthleteRepository athleteRepository;
    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Test
    void findByApplicationCode() {
        Organizer organizer = organizerRepository.save(Organizer.builder().name("Pepe").build());
        Athlete athlete = athleteRepository.save(Athlete.builder().name("Pepe").build());
        Race race = raceRepository.save(Race.builder().name("Carrera 1").location("Localización").organizer(organizer).build());
        Application application = applicationRepository.save(Application.builder()
                .applicationCode("111111")
                .applicationAthlete(athlete)
                .applicationRace(race)
                .build());
        Optional<Application> findApplication = applicationRepository.findByApplicationCode("111111");
        assertEquals(application.getId(),findApplication.get().getId());
    }

}