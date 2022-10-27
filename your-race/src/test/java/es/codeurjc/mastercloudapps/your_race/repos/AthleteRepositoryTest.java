package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.UniqueAbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AthleteRepositoryTest {
    @Autowired
    private AthleteRepository athleteRepository;

    @ClassRule
    public static UniqueAbstractDatabaseTest postgreSQLContainer = UniqueAbstractDatabaseTest.getInstance();

    @Test
    @Ignore
    void test() throws InterruptedException {
        Athlete createAthlete = athleteRepository.save(Athlete.builder().name("Perico").build());
        Optional<Athlete> findAthlete = athleteRepository.findById(createAthlete.getId());
        assertEquals("Perico",findAthlete.get().getName());
    }
}