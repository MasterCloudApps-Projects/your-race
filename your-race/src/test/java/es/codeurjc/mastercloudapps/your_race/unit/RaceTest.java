package es.codeurjc.mastercloudapps.your_race.unit;

import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class RaceTest extends AbstractDatabaseTest {

    @Test
    @DisplayName("Can create Race with name and location")
    void createRaceWithNameAndLocation(){
        Race race = new Race("Test Race","Santiago de Compostela");
        Assertions.assertFalse(race.getName().isEmpty());
        Assertions.assertFalse(race.getLocation().isEmpty());
    }

    @Test
    @DisplayName("Race and location are properly saved when new Race is created with name and location")
    void checkProperlySavedWhenCreatedWithNameAndLocation(){
        String name = "Test Race";
        String location = "Santiago de Compostela";
        Race race = new Race(name,location);
        Assertions.assertSame(race.getName(), name);
        Assertions.assertSame(race.getLocation(),location);

    }
}
