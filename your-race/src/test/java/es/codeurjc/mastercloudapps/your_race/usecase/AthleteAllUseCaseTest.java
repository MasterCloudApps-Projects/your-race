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






}
