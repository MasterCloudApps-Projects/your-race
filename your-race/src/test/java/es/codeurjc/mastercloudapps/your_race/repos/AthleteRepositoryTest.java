package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.UniqueAbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled
@SpringBootTest
class AthleteRepositoryTest {//extends AbstractDatabaseTest {
    @Autowired
    private AthleteRepository athleteRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = UniqueAbstractDatabaseTest.getInstance();

    @Test
    @Ignore
    void test() throws InterruptedException {
        Athlete createAthlete = athleteRepository.save(Athlete.builder().name("Perico").build());
        Optional<Athlete> findAthlete = athleteRepository.findById(createAthlete.getId());
        assertEquals("Perico",findAthlete.get().getName());
    }
}