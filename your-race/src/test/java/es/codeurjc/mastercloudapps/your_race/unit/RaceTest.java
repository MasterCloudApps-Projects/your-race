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
        Race race = Race.builder()
                .name(name)
                .location(location)
                .build();
        Assertions.assertSame(race.getName(), name);
        Assertions.assertSame(race.getLocation(),location);

    }

    @Test
    @DisplayName("A race has at least the following data: name, description, date, location, distance, type, athleteCapacity, ApplicationPeriod, Registration")
    void checkRaceHasData()
    {
        String name = "Test Race";
        String description = "A Test Race for testing";
        LocalDateTime date = LocalDateTime.of(2023, Month.MAY,13, 10,0);
        String location = "Santiago de Compostela";
        Double distance = 21.0975;
        String type = "Test";
        Integer athleteCapacity = 10000;
        LocalDateTime initialDay = LocalDateTime.of(2023, Month.JANUARY,1, 10,0);
        LocalDateTime lastDay = LocalDateTime.of(2023, Month.MARCH,1, 23,59);

        RegistrationType registrationType = RegistrationType.BYORDER;
        LocalDateTime registrationDate = LocalDateTime.of(2023, Month.MARCH,15, 10,0);
        Double registrationCost = 50.00;

        ApplicationPeriod applicationPeriod = ApplicationPeriod.builder()
                .initialDate(initialDay)
                .lastDate(lastDay)
                .build();
        Registration registration = Registration.builder()
                .registrationType(registrationType)
                .registrationDate(registrationDate)
                .registrationCost(registrationCost)
                .build();


        Race race = Race.builder()
                .name(name)
                .description(description)
                .date(date)
                .location(location)
                .distance(distance)
                .type(type)
                .athleteCapacity(athleteCapacity)
                .applicationPeriod(applicationPeriod)
                .raceRegistration(registration)
                .build();

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

    @DisplayName("Test a race is valid - Name and location are not empty")
    @Test
    void checkRaceIsValidNameAndLocation() {
        Race race1 = Race.builder()
                .name("Test Race")
                .location("Santiago de Compostela")
                .build();

        Assertions.assertTrue(race1.isValid());

        Race race2 = Race.builder()
                .build();
        Assertions.assertFalse(race2.isValid());
    }

    @DisplayName("Test a race is valid - Registration Type")
    @Test
    void checkRaceIsValidRegistrationType() {
        Race race = Race.builder()
                .name("Test Race")
                .location("Santiago de Compostela")
                .build();

        Registration registration = new Registration();
        race.setRaceRegistration(registration);

        Assertions.assertTrue(race.isValid());

        registration.setRegistrationType(RegistrationType.BYORDER);
        Assertions.assertTrue(race.isValid());

        registration.setRegistrationType(RegistrationType.BYDRAWING);
        Assertions.assertTrue(race.isValid());

    }




    @DisplayName("Test a race is valid - Athlete Capacity")
    @Test
    void checkRaceIsValidAthleteCapacity(){
        Race race = Race.builder()
                .name("Test Race")
                .location("Santiago de Compostela")
                .build();


        race.setAthleteCapacity(1000);
        Assertions.assertTrue(race.isValid());

        race.setAthleteCapacity(1);
        Assertions.assertTrue(race.isValid());

        race.setAthleteCapacity(0);
        Assertions.assertFalse(race.isValid());

        race.setAthleteCapacity(-1);
        Assertions.assertFalse(race.isValid());


    }

    @DisplayName("Test a race is valid - Distance")
    @Test
    void checkRaceIsValidDistance(){
        Race race = Race.builder()
                .name("Test Race")
                .location("Santiago de Compostela")
                .build();
        Assertions.assertTrue(race.isValid());

        race.setDistance(40.0);
        Assertions.assertTrue(race.isValid());

        race.setDistance(0.0);
        Assertions.assertFalse(race.isValid());

        race.setDistance(-1.0);
        Assertions.assertFalse(race.isValid());
    }

}
