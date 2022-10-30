package es.codeurjc.mastercloudapps.your_race.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationDTO;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationDTO;
import es.codeurjc.mastercloudapps.your_race.model.TrackDTO;
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

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
public class RegistrationUseCaseTest {

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


    @ClassRule
    public static AbstractDatabaseTest postgreSQLContainer = AbstractDatabaseTest.getInstance();


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

    @Test
    @DisplayName("An ahtlete should get a dorsal when sucessfully registrated in a Race")
        public void assignDorsalNumber() throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        MvcResult result = mvc.perform(post("/api/athletes/" + athleteList.get(0).getId()+"/applications/"+raceList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty()).andReturn();

        ApplicationDTO applicationDTO = mapper.readValue( result.getResponse().getContentAsString(), ApplicationDTO.class);


        String request = mapper.writeValueAsString(TestDataBuilder.produceRegistrationByOrder (applicationDTO));

        result = mvc.perform(post("/api/tracks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated()).andReturn();

        TrackDTO trackDTO = mapper.readValue(result.getResponse().getContentAsString(), TrackDTO.class);
        assertThat(trackDTO.getDorsal()).isNotNull();

    }

    @Test
    @DisplayName("An ahtlete should be sucessfully registrated only if there's capacity in the Race")
    public void checkRaceCapacity (){

    }
}
