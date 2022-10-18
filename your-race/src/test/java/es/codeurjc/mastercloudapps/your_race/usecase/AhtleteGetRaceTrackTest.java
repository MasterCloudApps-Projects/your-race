package es.codeurjc.mastercloudapps.your_race.usecase;

import com.github.javafaker.Faker;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Registration;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;
import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;
import es.codeurjc.mastercloudapps.your_race.repos.OrganizerRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
public class AhtleteGetRaceTrackTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    Organizer organizer;
    List<Race> raceList;
    List<Athlete> athleteList;

    private static class initializerData {
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
                    .raceRegistration(Registration.builder()
                            .registrationDate(LocalDateTime.now().plusMonths(4L))
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
    }

    @BeforeEach
    public void initEach(){

        initializerData.init();
        applicationRepository.deleteAll();
        raceRepository.deleteAll();
        organizerRepository.deleteAll();


        raceList = new ArrayList<Race>();
        athleteList = new ArrayList<Athlete>();

        organizer = initializerData.buildTestOrganizer();

        for (int i=0; i<3;i++)
            raceList.add(initializerData.buildTestRace(organizer));


        for (int i=0; i<2;i++)
            athleteList.add(initializerData.buildTestAthlete());

        organizerRepository.save(organizer);
        raceRepository.saveAll(raceList);
        athleteRepository.saveAll(athleteList);

    }

    @DisplayName("An athlete should get the races that has been registered to")
    @Test
    void shouldGetAthleteRegisteredRace() throws Exception{

        mvc.perform(get("/api/athletes/" + athleteList.get(0).getId()+"/tracks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());



    }
}

