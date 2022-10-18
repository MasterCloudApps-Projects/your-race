package es.codeurjc.mastercloudapps.your_race.usecase;

import com.github.javafaker.Faker;
import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.domain.*;
import es.codeurjc.mastercloudapps.your_race.repos.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Disabled
@AutoConfigureMockMvc
@SpringBootTest
public class AhtleteGetRaceTrackTest extends AbstractDatabaseTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private AthleteRepository athleteRepository;


    @Autowired
    private TrackRepository trackRepository;

    Organizer organizer;
    List<Race> raceList;
    List<Athlete> athleteList;
    List<Track> tracksList;

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

        static Track buildTrack(Athlete athlete, Race race){
            return Track.builder()
                    .athlete(athlete)
                    .race(race)
                    .status(faker.options().option("REGISTERED","PARTICIPATED"))
                    .registrationDate(race.getRaceRegistration().getRegistrationDate())
                    .dorsal(RandomUtils.nextInt())
                    .build();
        }


    }

    @BeforeEach
    public void initEach(){

        initializerData.init();
        trackRepository.deleteAll();
        raceRepository.deleteAll();
        organizerRepository.deleteAll();



        raceList = new ArrayList<Race>();
        athleteList = new ArrayList<Athlete>();
        tracksList = new ArrayList<Track>();

        organizer = initializerData.buildTestOrganizer();

        for (int i=0; i<3;i++)
            raceList.add(initializerData.buildTestRace(organizer));


        for (int i=0; i<3;i++)
            athleteList.add(initializerData.buildTestAthlete());

        organizerRepository.save(organizer);
        raceRepository.saveAll(raceList);
        athleteRepository.saveAll(athleteList);



    }


    @DisplayName("An athlete should get the races that has been registered to")
    @Test
    void shouldGetAthleteRegisteredRace() throws Exception{

       tracksList.add(initializerData.buildTrack(athleteList.get(0),raceList.get(0)));
       tracksList.add(initializerData.buildTrack(athleteList.get(0),raceList.get(1)));
       tracksList.add(initializerData.buildTrack(athleteList.get(0),raceList.get(2)));

       tracksList.add(initializerData.buildTrack(athleteList.get(1),raceList.get(1)));
       tracksList.add(initializerData.buildTrack(athleteList.get(1),raceList.get(2)));

       trackRepository.saveAll(tracksList);


        mvc.perform(get("/api/athletes/" + athleteList.get(0).getId()+"/tracks")
                .contentType(MediaType.APPLICATION_JSON)
                .param("open","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        mvc.perform(get("/api/athletes/" + athleteList.get(1).getId()+"/tracks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("open","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        mvc.perform(get("/api/athletes/" + athleteList.get(2).getId()+"/tracks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("open","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @DisplayName("An athlete should get the open races that has been registered to")
    @Test
    void shouldGetAthleteRegisteredOpenRace() throws Exception{

        initializerData.setDateInPast(raceList.get(0));
        initializerData.setDateInFuture(raceList.get(1));
        initializerData.setDateInFuture(raceList.get(2));

        tracksList.add(initializerData.buildTrack(athleteList.get(0),raceList.get(0)));
        tracksList.add(initializerData.buildTrack(athleteList.get(0),raceList.get(1)));
        tracksList.add(initializerData.buildTrack(athleteList.get(0),raceList.get(2)));


        raceRepository.saveAll(raceList);
        trackRepository.saveAll(tracksList);


        mvc.perform(get("/api/athletes/" + athleteList.get(0).getId()+"/tracks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("open","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));



    }
}

