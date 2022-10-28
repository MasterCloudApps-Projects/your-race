package es.codeurjc.mastercloudapps.your_race.acceptance;

import com.github.javafaker.Faker;
import es.codeurjc.mastercloudapps.your_race.domain.*;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;

class TestDataBuilder {

        private static Faker faker;

        static void init(){
            faker = new Faker();
        }


        static Race buildTestRace(Organizer organizer) {
            return Race.builder()
                    .name(faker.esports().event())
                    .description("A race for testing")
                    .location(faker.address().cityName())
                    .distance(faker.number().randomDouble(2, 0, 1000))
                    .organizer(organizer)
                    .raceRegistrationInfo(RegistrationInfo.builder()
                            .registrationDate(LocalDateTime.now().plusMonths(4L))
                            .build())
                    .applicationPeriod(ApplicationPeriod.builder()
                            .initialDate(LocalDateTime.now().minusMonths(1L))
                            .lastDate(LocalDateTime.now().plusMonths(1L))
                            .build())
                    .date(LocalDateTime.now().plusMonths(6L))
                    .build();
        }

        static void setDateInFuture(Race race) {

            LocalDateTime date = LocalDateTime.now().plusMonths(4L);
            race.setDate(LocalDateTime.of(date.getYear(), date.getMonth(), 1, 9, 0));

        }

        static void setDateInPast(Race race) {

            LocalDateTime date = LocalDateTime.now().minusMonths(1L);
            race.setDate(LocalDateTime.of(date.getYear(), date.getMonth(), 1, 9, 0));

        }

        static Athlete buildTestAthlete() {
            return Athlete.builder().name(faker.name().firstName()).surname(faker.name().lastName()).build();
        }
        static Organizer buildTestOrganizer(){
            return Organizer.builder().name("Test Organizer").build();
        }

        static Track buildTrack(Athlete athlete, Race race){
            return Track.builder()
                    .athlete(athlete)
                    .race(race)
                    .status(faker.options().option("REGISTERED","PARTICIPATED"))
                    .registrationDate(race.getRaceRegistrationInfo().getRegistrationDate())
                    .dorsal(RandomUtils.nextInt())
                    .build();
        }
        static void setApplicationPeriodOpen(Race race){
            race.getApplicationPeriod().setInitialDate(LocalDateTime.now().minusMonths(1L));
            race.getApplicationPeriod().setLastDate(LocalDateTime.now().plusMonths(2));
        }

        static void setApplicationPeriodClosed(Race race){
            race.getApplicationPeriod().setInitialDate(LocalDateTime.now().minusMonths(3L));
            race.getApplicationPeriod().setLastDate(LocalDateTime.now().minusMonths(2));
        }

    }

