package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class AthleteRepositoryTest extends AbstractDatabaseTest {
    @Autowired
    private AthleteRepository athleteRepository;

    @Test
    void test() throws InterruptedException {
        Athlete createAthlete = athleteRepository.save(Athlete.builder().name("Perico").build());
        Optional<Athlete> findAthlete = athleteRepository.findById(createAthlete.getId());
        assertEquals("Perico",findAthlete.get().getName());
    }
}