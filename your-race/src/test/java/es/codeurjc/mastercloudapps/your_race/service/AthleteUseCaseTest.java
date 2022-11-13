package es.codeurjc.mastercloudapps.your_race.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Race;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Track;
import es.codeurjc.mastercloudapps.your_race.model.*;

import es.codeurjc.mastercloudapps.your_race.repos.sql.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

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
@ActiveProfiles("postgres")
public class AthleteUseCaseTest {
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

        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(ApplicationRequestDTO.builder()
                .athleteId(athleteList.get(0).getId().toString())
                .raceId(raceList.get(0).getId().toString()).build());

        mvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty());

        request = mapper.writeValueAsString(ApplicationRequestDTO.builder()
                .athleteId("0")
                .raceId("0").build());

        mvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }


    @DisplayName("Application to non-existing Athlete and/or race should not be possible")
    @Test
    void applicationToNonExistingAthleteRaceShouldNotBePossible() throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(ApplicationRequestDTO.builder()
                .athleteId("0")
                .raceId(raceList.get(0).getId().toString()).build());

        mvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());

        request = mapper.writeValueAsString(ApplicationRequestDTO.builder()
                .athleteId(athleteList.get(0).getId().toString())
                .raceId("0").build());

        mvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());

        request = mapper.writeValueAsString(ApplicationRequestDTO.builder()
                .athleteId("0")
                .raceId("0").build());

        mvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Races that an athlete has applied to")
    @Test
    void shouldGetAthleteApplicationRacesList() throws Exception {

        TestDataBuilder.athleteApplyToRace(mvc,athleteList.get(0), raceList.get(0));
        TestDataBuilder.athleteApplyToRace(mvc,athleteList.get(0), raceList.get(1));
        TestDataBuilder.athleteApplyToRace(mvc,athleteList.get(1), raceList.get(1));
        
        mvc.perform(get("/api/applications/athletes/" + athleteList.get(0).getId())
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

        TestDataBuilder.athleteApplyToRace(mvc,athleteList.get(0), raceList.get(0));
        TestDataBuilder.athleteApplyToRace(mvc,athleteList.get(0), raceList.get(1));
        TestDataBuilder.athleteApplyToRace(mvc,athleteList.get(0), raceList.get(2));

        mvc.perform(get("/api/applications/athletes/" + athleteList.get(0).getId())
                        .param("open","true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        mvc.perform(get("/api/applications/athletes/" + athleteList.get(0).getId())
                        .param("open","false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @DisplayName("Athlete should apply to a race only if ApplicationPeriod is open")
    @Test
    void athleteShouldApplyIfApplicationPeriodIsOpen() throws Exception{
        
        TestDataBuilder.setDateInFuture(raceList.get(0));
        TestDataBuilder.setApplicationPeriodClosed(raceList.get(0));

        raceRepository.saveAll(raceList);

        ObjectMapper mapper = new ObjectMapper();
        String  request = mapper.writeValueAsString(ApplicationRequestDTO.builder()
                .athleteId(athleteList.get(0).getId().toString())
                .raceId(raceList.get(0).getId().toString()).build());

        mvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());

        TestDataBuilder.setApplicationPeriodOpen(raceList.get(0));
        raceRepository.saveAll(raceList);

        mvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
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
        String request = mapper.writeValueAsString(TrackRequestDTO.builder().athleteId(athleteList.get(0).getId()).build());

        mvc.perform(get("/api/tracks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .param("open","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        request = mapper.writeValueAsString(TrackRequestDTO.builder().athleteId(athleteList.get(1).getId()).build());

        mvc.perform(get("/api/tracks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .param("open","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        request = mapper.writeValueAsString(TrackRequestDTO.builder().athleteId(athleteList.get(2).getId()).build());

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
        String request = mapper.writeValueAsString(TrackRequestDTO.builder().athleteId(athleteList.get(0).getId()).build());

        mvc.perform(get("/api/tracks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .param("open","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
