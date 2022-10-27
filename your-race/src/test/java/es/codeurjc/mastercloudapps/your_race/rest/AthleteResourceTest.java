package es.codeurjc.mastercloudapps.your_race.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.UniqueAbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import es.codeurjc.mastercloudapps.your_race.model.*;
import es.codeurjc.mastercloudapps.your_race.repos.*;

import org.junit.ClassRule;
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
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Disabled
@AutoConfigureMockMvc
@SpringBootTest
public class AthleteResourceTest //extends UniqueAbstractDatabaseTest {
{
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


    @ClassRule
    public static UniqueAbstractDatabaseTest postgreSQLContainer = UniqueAbstractDatabaseTest.getInstance();


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

        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(QueryTrackDTO.builder().athleteId(athleteList.get(0).getId()).build());

        mvc.perform(get("/api/tracks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .param("open","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        request = mapper.writeValueAsString(QueryTrackDTO.builder().athleteId(athleteList.get(1).getId()).build());

        mvc.perform(get("/api/tracks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .param("open","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        request = mapper.writeValueAsString(QueryTrackDTO.builder().athleteId(athleteList.get(2).getId()).build());

        mvc.perform(get("/api/tracks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
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


        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(QueryTrackDTO.builder().athleteId(athleteList.get(0).getId()).build());

        mvc.perform(get("/api/tracks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .param("open","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));



    }




}
