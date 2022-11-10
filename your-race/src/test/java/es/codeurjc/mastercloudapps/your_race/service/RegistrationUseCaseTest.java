package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.sql.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Race;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Track;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationDTO;
import es.codeurjc.mastercloudapps.your_race.model.TrackDTO;
import es.codeurjc.mastercloudapps.your_race.repos.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("postgres")
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

    @Test
    @DisplayName("An athlete should be sucessfully registrated only if there's capacity in the Race")
    public void checkRaceCapacity () throws Exception{

        raceList.get(0).setAthleteCapacity(3);
        TrackDTO trackDTO = TestDataBuilder.registerAthleteToRaceByOrder(mvc,athleteList.get(0),raceList.get(0));
        TrackDTO trackDTO1 = TestDataBuilder.registerAthleteToRaceByOrder(mvc,athleteList.get(1),raceList.get(0));
        TrackDTO trackDTO2= TestDataBuilder.registerAthleteToRaceByOrder(mvc,athleteList.get(2),raceList.get(0));

        ApplicationDTO applicationDTO = TestDataBuilder.athleteApplyToRace(mvc, athleteList.get(3),raceList.get(0));

        mvc.perform(post("/api/tracks/byorder/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestDataBuilder.generateRegistrationByOrderBodyRequest(applicationDTO.getApplicationCode(),athleteList.get(3).getId(),raceList.get(0).getId())))
                .andExpect(status().isBadRequest());


    }
    @Test
    @DisplayName("An organizer should get the list of applications to a race")
    public void checkRaceApplications () throws Exception{

        raceList.get(0).setAthleteCapacity(3);
        ApplicationDTO applicationDTO = TestDataBuilder.athleteApplyToRace(mvc,athleteList.get(0),raceList.get(0));
        ApplicationDTO applicationDTO1 = TestDataBuilder.athleteApplyToRace(mvc,athleteList.get(1),raceList.get(0));
        ApplicationDTO applicationDTO2 = TestDataBuilder.athleteApplyToRace(mvc,athleteList.get(2),raceList.get(0));


        mvc.perform(get("/api/applications/races/" + raceList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }
}
