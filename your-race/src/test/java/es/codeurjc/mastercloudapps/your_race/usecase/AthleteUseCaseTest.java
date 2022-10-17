package es.codeurjc.mastercloudapps.your_race.usecase;

import com.github.javafaker.Faker;
import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Registration;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;
import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;
import es.codeurjc.mastercloudapps.your_race.repos.OrganizerRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import es.codeurjc.mastercloudapps.your_race.service.RaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
public class AthleteUseCaseTest extends AbstractDatabaseTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private RaceService raceService;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    private Faker faker;
    Organizer organizer;




    @BeforeEach
    public void initEach(){
        faker = new Faker();
        applicationRepository.deleteAll();
        raceRepository.deleteAll();

    }

    @DisplayName("Get list of open races (not celebrated yet)")
    @Test
    void shouldGetListOpenRaces() throws Exception{

        Organizer organizer = Organizer.builder().name("Test Organizer").build();
        organizerRepository.save(organizer);

        Race race = buildTestRace(organizer);
        Race plannedRace1 = buildTestRace(organizer);
        Race plannedRace2 = buildTestRace(organizer);


        this.setDateInPast(race);
        this.setDateInFuture(plannedRace1);
        this.setDateInFuture(plannedRace2);

        raceRepository.save(race);
        raceRepository.save(plannedRace1);
        raceRepository.save(plannedRace2);



        mvc.perform(get("/api/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("open","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));



    }

    Race buildTestRace(Organizer organizer){
        return Race.builder()
                .name(faker.esports().event())
                .description("A race for testing")
                .location(faker.address().cityName())
                .distance(faker.number().randomDouble(2,0,1000))
                .organizer(organizer)
                .raceRegistration(Registration.builder()
                        .registrationDate( LocalDateTime.now().plusMonths(4L))
                        .build())
                .date(LocalDateTime.now().plusMonths(6L))
                .build();
    }
    void setDateInFuture(Race race){

        LocalDateTime date = LocalDateTime.now().plusMonths(4L);
        race.setDate(LocalDateTime.of(date.getYear(), date.getMonth(),1,9,0));

    }

    void setDateInPast(Race race){

        LocalDateTime date = LocalDateTime.now().minusMonths(1L);
        race.setDate(LocalDateTime.of(date.getYear(), date.getMonth(),1,9,0));

    }

    @DisplayName("Existing athlete should apply to existing race")
    @Test
    void athleteShouldApplyToExistingRace() throws Exception{

        Athlete athlete = Athlete.builder().name("Raquel").surname("Toscano").build();
        athleteRepository.save(athlete);

        Organizer organizer = Organizer.builder().name("Test Organizer").build();
        organizerRepository.save(organizer);

        Race race = buildTestRace(organizer);
        raceRepository.save(race);

       mvc.perform(post("/api/athletes/" + athlete.getId()+"/application/"+race.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());

        mvc.perform(post("/api/athletes/" + "0000" +"/application/"+ "0000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @DisplayName("Application to non-existing Athlete and/or race should not be possible")
    @Test
    void applicationToNonExistingAthleteRaceShouldNotBePossible() throws Exception{

        Athlete athlete = Athlete.builder().name("Raquel").surname("Toscano").build();
        athleteRepository.save(athlete);

        Organizer organizer = Organizer.builder().name("Test Organizer").build();
        organizerRepository.save(organizer);

        Race race = buildTestRace(organizer);
        raceRepository.save(race);

        mvc.perform(post("/api/athletes/" + "0000" + "/application/"+race.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(post("/api/athletes/" + athlete.getId()+"/application/" + "0000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(post("/api/athletes/" + "0000" +"/application/"+ "0000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Races that an athlete has applied to")
    @Test
    void shouldGetAthleteApplicationRacesList() throws Exception {

        Athlete athlete = Athlete.builder().name("Raquel").surname("Toscano").build();
        athleteRepository.save(athlete);

        Athlete athlete2 = Athlete.builder().name("Rafael").surname("Gómez").build();
        athleteRepository.save(athlete2);

        Organizer organizer = Organizer.builder().name("Test Organizer").build();
        organizerRepository.save(organizer);

        Race race1 = buildTestRace(organizer);
        raceRepository.save(race1);

        Race race2 = buildTestRace(organizer);
        raceRepository.save(race2);

        Race race3 = buildTestRace(organizer);
        raceRepository.save(race3);

        mvc.perform(post("/api/athletes/" + athlete.getId()+"/application/"+race1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());

        mvc.perform(post("/api/athletes/" + athlete.getId()+"/application/"+race2.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());


        mvc.perform(post("/api/athletes/" + athlete2.getId()+"/application/"+race1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());


        mvc.perform(get("/api/athletes/" + athlete.getId()+"/applications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].raceName", is(race1.getName())));

    }


    @DisplayName("Races that an athlete has applied to and are open")
    @Test
    void shouldGetAthleteApplicationOpenRacesList() throws Exception {

        Athlete athlete = Athlete.builder().name("Raquel").surname("Toscano").build();
        athleteRepository.save(athlete);


        Organizer organizer = Organizer.builder().name("Test Organizer").build();
        organizerRepository.save(organizer);

        Race race1 = buildTestRace(organizer);
        Race race2 = buildTestRace(organizer);
        Race race3 = buildTestRace(organizer);

        this.setDateInPast(race1);
        this.setDateInFuture(race2);
        this.setDateInFuture(race3);

        raceRepository.save(race1);
        raceRepository.save(race2);
        raceRepository.save(race3);

        mvc.perform(post("/api/athletes/" + athlete.getId()+"/application/"+race1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());

        mvc.perform(post("/api/athletes/" + athlete.getId()+"/application/"+race2.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());


        mvc.perform(post("/api/athletes/" + athlete.getId()+"/application/"+race3.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());


        mvc.perform(get("/api/athletes/" + athlete.getId()+"/applications")
                        .param("open","true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

 }
