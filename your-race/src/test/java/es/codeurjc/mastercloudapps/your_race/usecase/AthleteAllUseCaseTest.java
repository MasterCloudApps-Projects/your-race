package es.codeurjc.mastercloudapps.your_race.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;

import es.codeurjc.mastercloudapps.your_race.domain.*;
import es.codeurjc.mastercloudapps.your_race.model.*;
import es.codeurjc.mastercloudapps.your_race.repos.*;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@AutoConfigureMockMvc
@SpringBootTest
public class AthleteAllUseCaseTest extends AbstractDatabaseTest {
    @Autowired
    private MockMvc mvc;

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



 /*   private static class initializerData {
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

    }*/


    @BeforeEach
    public void initEach(){

        TestDataBuilder.init();
        trackRepository.deleteAll();
        applicationRepository.deleteAll();
        raceRepository.deleteAll();
        organizerRepository.deleteAll();



        raceList = new ArrayList<Race>();
        athleteList = new ArrayList<Athlete>();
        tracksList = new ArrayList<Track>();

        organizer = TestDataBuilder.buildTestOrganizer();

        for (int i=0; i<3;i++)
            raceList.add(TestDataBuilder.buildTestRace(organizer));


        for (int i=0; i<3;i++)
            athleteList.add(TestDataBuilder.buildTestAthlete());

        organizerRepository.save(organizer);
        raceRepository.saveAll(raceList);
        athleteRepository.saveAll(athleteList);


    }

    @DisplayName("Get list of open races (not celebrated yet)")
    @Test
    void shouldGetListOpenRaces() throws Exception{

        TestDataBuilder.setDateInPast(raceList.get(0));
        TestDataBuilder.setDateInFuture(raceList.get(1));
        TestDataBuilder.setDateInFuture(raceList.get(2));

        raceRepository.saveAll(raceList);

        mvc.perform(get("/api/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("open","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        mvc.perform(get("/api/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("open","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }



    @DisplayName("Existing athlete should apply to existing race")
    @Test
    void athleteShouldApplyToExistingRace() throws Exception{

        mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/"+raceList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());

        mvc.perform(post("/api/athletes/" + "0000" +"/applications/"+ "0000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @DisplayName("Application to non-existing Athlete and/or race should not be possible")
    @Test
    void applicationToNonExistingAthleteRaceShouldNotBePossible() throws Exception{

        mvc.perform(post("/api/athletes/" + "0000" + "/applications/"+raceList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/" + "0000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(post("/api/athletes/" + "0000" +"/applications/"+ "0000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Races that an athlete has applied to")
    @Test
    void shouldGetAthleteApplicationRacesList() throws Exception {

        mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/"+raceList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());

        mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/"+raceList.get(1).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());


        mvc.perform(post("/api/athletes/" + athleteList.get(1).getId() +"/applications/"+raceList.get(1).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());


        mvc.perform(get("/api/athletes/" + athleteList.get(0).getId()+"/applications")
                        .param("open","false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].raceName", is(raceList.get(0).getName())));

    }


    @DisplayName("Races that an athlete has applied to and are open")
    @Test
    void shouldGetAthleteApplicationOpenRacesList() throws Exception {

        TestDataBuilder.setDateInPast(raceList.get(0));
        TestDataBuilder.setDateInFuture(raceList.get(1));
        TestDataBuilder.setDateInFuture(raceList.get(2));

        raceRepository.saveAll(raceList);

        mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/"+raceList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());

        mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/"+raceList.get(1).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());


        mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/"+raceList.get(2).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());


        mvc.perform(get("/api/athletes/" + athleteList.get(0).getId()+"/applications")
                        .param("open","true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        mvc.perform(get("/api/athletes/" + athleteList.get(0).getId()+"/applications")
                        .param("open","false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @DisplayName("Athlete should apply to a race only if ApplicationPeriod is open")
    @Test
    void athleteShouldApplyIfAppliactionPeriodIsOpen() throws Exception{


        TestDataBuilder.setDateInFuture(raceList.get(0));
        TestDataBuilder.setApplicationPeriodClosed(raceList.get(0));


        raceRepository.saveAll(raceList);

        mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/"+raceList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());

        TestDataBuilder.setApplicationPeriodOpen(raceList.get(0));
        raceRepository.saveAll(raceList);

        mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/"+raceList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());

    }

    @DisplayName("An athlete should get the races that has been registered to")
    @Test
    void shouldGetAthleteRegisteredRace() throws Exception{

        tracksList.add(TestDataBuilder.buildTrack(athleteList.get(0),raceList.get(0)));
        tracksList.add(TestDataBuilder.buildTrack(athleteList.get(0),raceList.get(1)));
        tracksList.add(TestDataBuilder.buildTrack(athleteList.get(0),raceList.get(2)));

        tracksList.add(TestDataBuilder.buildTrack(athleteList.get(1),raceList.get(1)));
        tracksList.add(TestDataBuilder.buildTrack(athleteList.get(1),raceList.get(2)));

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

        TestDataBuilder.setDateInPast(raceList.get(0));
        TestDataBuilder.setDateInFuture(raceList.get(1));
        TestDataBuilder.setDateInFuture(raceList.get(2));

        tracksList.add(TestDataBuilder.buildTrack(athleteList.get(0),raceList.get(0)));
        tracksList.add(TestDataBuilder.buildTrack(athleteList.get(0),raceList.get(1)));
        tracksList.add(TestDataBuilder.buildTrack(athleteList.get(0),raceList.get(2)));


        raceRepository.saveAll(raceList);
        trackRepository.saveAll(tracksList);


        mvc.perform(get("/api/athletes/" + athleteList.get(0).getId()+"/tracks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("open","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));



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
        mvc.perform(post("/api/tracks/")
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

        mvc.perform(post("/api/tracks/")
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


        mvc.perform(post("/api/tracks/")
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
