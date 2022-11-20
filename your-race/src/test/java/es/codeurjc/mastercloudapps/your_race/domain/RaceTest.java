


package es.codeurjc.mastercloudapps.your_race.domain;

import com.github.javafaker.Faker;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
@ActiveProfiles("postgres")
class RaceTest {

    Faker faker;
    @BeforeEach
    public void initEach(){
        faker = new Faker();
    }



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
        RegistrationInfo registrationInfo = RegistrationInfo.builder()
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
                .raceRegistrationInfo(registrationInfo)
                .build();

        Assertions.assertSame(race.getName(), name);
        Assertions.assertSame(race.getDescription(), description);
        Assertions.assertSame(race.getDate(), date);
        Assertions.assertSame(race.getLocation(), location);
        Assertions.assertSame(race.getDistance(), distance);
        Assertions.assertSame(race.getType(), type);
        Assertions.assertSame(race.getAthleteCapacity(), athleteCapacity);
        Assertions.assertSame(race.getApplicationPeriod(), applicationPeriod);
        Assertions.assertSame(race.getRaceRegistrationInfo(), registrationInfo);
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

        RegistrationInfo registrationInfo = new RegistrationInfo();
        race.setRaceRegistrationInfo(registrationInfo);

        Assertions.assertTrue(race.isValid());

        registrationInfo.setRegistrationType(RegistrationType.BYORDER);
        Assertions.assertTrue(race.isValid());

        registrationInfo.setRegistrationType(RegistrationType.BYDRAW);
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

    @DisplayName("Test a race is valid - ConcurrentRequestThreshold")
    @Test
    void checkRaceIsValidConcurrentRequestThreshold(){
        Race race = Race.builder()
                .name("Test Race")
                .location("Santiago de Compostela")
                .build();

        RegistrationInfo registrationInfo = new RegistrationInfo();
        race.setRaceRegistrationInfo(registrationInfo);

        registrationInfo.setConcurrentRequestThreshold(5000);
        Assertions.assertTrue(race.isValid());

        registrationInfo.setConcurrentRequestThreshold(2);
        Assertions.assertTrue(race.isValid());

        registrationInfo.setConcurrentRequestThreshold(1);
        Assertions.assertFalse(race.isValid());


    }

    @DisplayName("Test a race is valid - Registration Date")
    @Test
    void checkRaceIsValidRegistrationDate() {
        Race race = Race.builder()
                .name(faker.name().name())
                .location(faker.address().fullAddress())
                .build();

        Assertions.assertTrue(race.isValid());

        RegistrationInfo registrationInfo = new RegistrationInfo();
        race.setRaceRegistrationInfo(registrationInfo);

        registrationInfo.setRegistrationDate(LocalDateTime.of(2023,Month.JANUARY,1,9,0,0));
        Assertions.assertTrue(race.isValid());


        registrationInfo.setRegistrationDate(LocalDateTime.of(2022,Month.JANUARY,1,9,0,0));
        Assertions.assertFalse(race.isValid());


    }

    @DisplayName("Test a race is valid - Application Date")
    @Test
    void checkRaceIsValidApplicationDate() {
        Race race = Race.builder()
                .name("Test Race")
                .location("Santiago de Compostela")
                .build();

        ApplicationPeriod applicationPeriod = new ApplicationPeriod();
        race.setApplicationPeriod(applicationPeriod);

        applicationPeriod.setInitialDate(LocalDateTime.of(2022,Month.NOVEMBER,1,9,0));
        applicationPeriod.setLastDate(LocalDateTime.of(2022,Month.DECEMBER,31,23,59));

        Assertions.assertTrue(race.isValid());

        applicationPeriod.setInitialDate(LocalDateTime.of(2023,Month.NOVEMBER,1,9,0));
        applicationPeriod.setLastDate(LocalDateTime.of(2022,Month.DECEMBER,31,23,59));
        Assertions.assertFalse(race.isValid());

        applicationPeriod.setInitialDate(null);
        Assertions.assertFalse(race.isValid());

        applicationPeriod.setInitialDate(LocalDateTime.of(2023,Month.NOVEMBER,1,9,0));
        applicationPeriod.setLastDate(null);
        Assertions.assertFalse(race.isValid());



    }

    @DisplayName("Test a race is valid - Dates are valid")
    @Test
    void checkRaceIsValidDatesAreValid(){

        Race race = Race.builder()
                .name("Test Race")
                .location("Santiago de Compostela")
                .build();


        ApplicationPeriod applicationPeriod = new ApplicationPeriod();
        race.setApplicationPeriod(applicationPeriod);

        RegistrationInfo registrationInfo = new RegistrationInfo();
        race.setRaceRegistrationInfo(registrationInfo);

        Assertions.assertTrue(race.isValid());

        registrationInfo.setRegistrationDate(LocalDateTime.of(2023,Month.JANUARY,1,9,0,0));
        Assertions.assertTrue(race.isValid());

        registrationInfo.setRegistrationDate(null);
        applicationPeriod.setInitialDate(LocalDateTime.of(2022,Month.NOVEMBER,1,9,0));
        applicationPeriod.setLastDate(LocalDateTime.of(2022,Month.DECEMBER,31,23,59));

        Assertions.assertTrue(race.isValid());

        registrationInfo.setRegistrationDate(LocalDateTime.of(2023,Month.JANUARY,1,9,0,0));
        applicationPeriod.setInitialDate(LocalDateTime.of(2022,Month.NOVEMBER,1,9,0));
        applicationPeriod.setLastDate(LocalDateTime.of(2022,Month.DECEMBER,31,23,59));

        Assertions.assertTrue(race.isValid());

        registrationInfo.setRegistrationDate(LocalDateTime.of(2022,Month.JANUARY,1,9,0,0));
        applicationPeriod.setInitialDate(LocalDateTime.of(2022,Month.NOVEMBER,1,9,0));
        applicationPeriod.setLastDate(LocalDateTime.of(2022,Month.DECEMBER,31,23,59));

        Assertions.assertFalse(race.isValid());

    }
}
