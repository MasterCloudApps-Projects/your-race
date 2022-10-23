package es.codeurjc.mastercloudapps.your_race.usecase;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.domain.*;
import es.codeurjc.mastercloudapps.your_race.model.*;
import es.codeurjc.mastercloudapps.your_race.repos.*;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;




@SpringBootTest
@AutoConfigureMockMvc
public class AthleteRaceRegistrationTest extends AbstractDatabaseTest {

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

    @Autowired
    private ApplicationRepository applicationRepository;

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

    @BeforeEach
    public void initEach(){

        initializerData.init();
        trackRepository.deleteAll();
        applicationRepository.deleteAll();
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
    @DisplayName("An athlete with application should register to race (ByOrder registration)")
    @Test
    void athleteShouldRegisterToRace() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();

        MvcResult result = mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/"+raceList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty()).andReturn();

        ApplicationDTO applicationDTO = mapper.readValue( result.getResponse().getContentAsString(), ApplicationDTO.class);


        String request = mapper.writeValueAsString(produceRegistrationByOrder(applicationDTO));
        mvc.perform(post("/api/registrations/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated());

    }




    @DisplayName("An athlete with non existing application code should not register to race (ByOrder registration)")
    @Test
    void athleteShouldNotRegisterToRace() throws Exception
    {

        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(produceRegistrationByOrder(
                ApplicationDTO.builder()
                        .applicationCode("APPLICATION_CODE_TEST")
                        .build()));

        mvc.perform(post("/api/registrations/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());

    }


    @DisplayName("An organizer should register an athlete to a race (ByDraw registration)")
    @Test
    void organizerShouldRegisterAthleteToRace() throws Exception
    {


        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(produceRegistrationByDraw(athleteList.get(0),raceList.get(0)));


        mvc.perform(post("/api/registrations/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
    }


    RegistrationDTO produceRegistrationByOrder(ApplicationDTO applicationDTO){
       return RegistrationByOrderDTO.builder()
                .registrationType(RegistrationType.BYORDER)
                .applicationCode(applicationDTO.getApplicationCode())
                .build();
    }

    RegistrationDTO produceRegistrationByDraw(Athlete athlete, Race race){
        return RegistrationByDrawDTO.builder()
                .registrationType(RegistrationType.BYDRAWING)
                .idAthlete(athlete.getId())
                .idRace(race.getId())
                .build();

    }
}
