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


        for (int i=0; i<4;i++)
            athleteList.add(TestDataBuilder.buildTestAthlete());

        organizerRepository.save(organizer);
        raceRepository.saveAll(raceList);
        athleteRepository.saveAll(athleteList);


    }

    @Test
    @DisplayName("An athlete should get a dorsal when sucessfully registrated in a Race")
        public void assignDorsalNumber() throws Exception{

        TrackDTO trackDTO = TestDataBuilder.registerAthleteToRaceByOrder(mvc,athleteList.get(0),raceList.get(0));

        assertThat(trackDTO.getDorsal()).isNotNull();
        assertThat(trackDTO.getDorsal()).isEqualTo(Integer.valueOf(1));


    }

    @Test
    @DisplayName("Dorsals are provided in a consecutive order of registration")
    public void checkDorsalAssignation () throws Exception{

        raceList.get(0).setAthleteCapacity(3);
        TrackDTO trackDTO = TestDataBuilder.registerAthleteToRaceByOrder(mvc,athleteList.get(0),raceList.get(0));
        TrackDTO trackDTO1 = TestDataBuilder.registerAthleteToRaceByOrder(mvc,athleteList.get(1),raceList.get(0));
        TrackDTO trackDTO2= TestDataBuilder.registerAthleteToRaceByOrder(mvc,athleteList.get(2),raceList.get(0));

        assertThat(trackDTO.getDorsal()).isEqualTo(Integer.valueOf(1));
        assertThat(trackDTO1.getDorsal()).isEqualTo(Integer.valueOf(2));
        assertThat(trackDTO2.getDorsal()).isEqualTo(Integer.valueOf(3));

    }


}
