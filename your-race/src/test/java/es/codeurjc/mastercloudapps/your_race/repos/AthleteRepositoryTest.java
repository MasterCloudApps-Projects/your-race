package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class AthleteRepositoryTest extends AbstractDatabaseTest {
    @Autowired
    private AthleteRepository athleteRepository;

    @Test
    void test() throws InterruptedException {
        List<Athlete> athletas = athleteRepository.findAll();
        assertEquals(3, athletas.size());
    }
}