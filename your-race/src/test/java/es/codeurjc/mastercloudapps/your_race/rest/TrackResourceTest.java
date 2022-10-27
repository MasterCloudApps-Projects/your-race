package es.codeurjc.mastercloudapps.your_race.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.codeurjc.mastercloudapps.your_race.UniqueAbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import es.codeurjc.mastercloudapps.your_race.model.*;
import es.codeurjc.mastercloudapps.your_race.repos.*;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class TrackResourceTest {

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
                .registrationType(RegistrationType.BYDRAW)
                .idAthlete(athlete.getId())
                .idRace(race.getId())
                .build();

    }
}
