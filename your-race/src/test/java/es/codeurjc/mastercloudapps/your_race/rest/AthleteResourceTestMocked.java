package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.UniqueAbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.model.AthleteDTO;
import es.codeurjc.mastercloudapps.your_race.service.AthleteService;
import org.junit.ClassRule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

//@Disabled
@SpringBootTest
@AutoConfigureMockMvc
class AthleteResourceTestMocked  { //extends UniqueAbstractDatabaseTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AthleteService athleteService;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = UniqueAbstractDatabaseTest.getInstance();

    @Test
    void getAthletesTest() throws Exception {

        List<AthleteDTO> athletes = Arrays.asList(AthleteDTO.builder().name("Pepe").build(),AthleteDTO.builder().name("Manolo").build());

        when(athleteService.findAll()).thenReturn(athletes);

        mvc.perform(get("/api/athletes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", equalTo("Pepe")));

    }
}