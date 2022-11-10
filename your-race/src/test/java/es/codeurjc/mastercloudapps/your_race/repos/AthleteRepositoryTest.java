package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.sql.Athlete;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("postgres")
class AthleteRepositoryTest {
    @Autowired
    private AthleteRepository athleteRepository;

    @Test
    void test() throws InterruptedException {
        Athlete createAthlete = athleteRepository.save(Athlete.builder().name("Perico").build());
        Optional<Athlete> findAthlete = athleteRepository.findById(createAthlete.getId());
        assertEquals("Perico",findAthlete.get().getName());
    }
}