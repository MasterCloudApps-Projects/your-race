package es.codeurjc.mastercloudapps.your_race.unit;

import es.codeurjc.mastercloudapps.your_race.domain.Race;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RaceTest {

    @Test
    @DisplayName("Can create Race with name and location")
    void createRaceWithNameAndLocation(){
        Race race = new Race("Test Race","Santiago de Compostela");
        Assertions.assertFalse(race.getName().isEmpty());
        Assertions.assertFalse(race.getLocation().isEmpty());
    }
}
