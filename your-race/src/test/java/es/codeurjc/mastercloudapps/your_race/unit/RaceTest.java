package es.codeurjc.mastercloudapps.your_race.unit;

import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.domain.ApplicationPeriod;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Registration;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.time.Month;


@SpringBootTest
class RaceTest extends AbstractDatabaseTest {

    @Test
    @DisplayName("Can create Race with name and location")
    void createRaceWithNameAndLocation(){
        String name = "Test Race";
        String location = "Santiago de Compostela";
        Race race = new Race(name,location);
        Assertions.assertSame(race.getName(), name);
        Assertions.assertSame(race.getLocation(),location);

    }

    @Test
    @DisplayName("A race has at least the following data: name, description, date, location, distance, type, athleteCapacity, ApplicationPeriod, Registration")
    void checkRaceData()
    {
        String name = "Test Race";
        String description = "A Test Race for testing";
        LocalDateTime date = LocalDateTime.of(2023, Month.MAY,13, 10,0);
        String location = "Santiago de Compostela";
        double distance = 21.0975;
        String type = "Test";
        int athleteCapacity = 10000;

        ApplicationPeriod applicationPeriod = new ApplicationPeriod();
        Registration registration = new Registration( );


        Race race = new Race();

        Assertions.assertSame(race.getName(), name);
        Assertions.assertSame(race.getDescription(), description);
        Assertions.assertSame(race.getDate(), date);
        Assertions.assertSame(race.getLocation(), location);
        Assertions.assertSame(race.getDistance(), distance);
        Assertions.assertSame(race.getType(), type);
        Assertions.assertSame(race.getAthleteCapacity(), athleteCapacity);
        Assertions.assertSame(race.getApplicationPeriod(), applicationPeriod);
        Assertions.assertSame(race.getRaceRegistration(), registration);

    }


}
