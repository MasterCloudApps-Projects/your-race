package es.codeurjc.mastercloudapps.your_race.rest;


import es.codeurjc.mastercloudapps.your_race.AbstractDatabaseTest;
import es.codeurjc.mastercloudapps.your_race.model.RaceDTO;
import es.codeurjc.mastercloudapps.your_race.service.RaceService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class RaceResourceTestMocked extends AbstractDatabaseTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RaceService raceService;

    @Test
    void getRacesTest() throws Exception {

        List<RaceDTO> races = Arrays.asList(RaceDTO.builder().name("Ronda").build(),RaceDTO.builder().name("NYC").build());

        when(raceService.findAll()).thenReturn(races);

        mvc.perform(get("/api/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("open","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", equalTo("Ronda")));

    }

    @Test
    void createRaceTest() throws Exception {

       RaceDTO race = RaceDTO.builder().name("Ronda").location("Ficticia").organizerName("La Legión").build();

         when(raceService.create(any(RaceDTO.class))).thenReturn(1L);

        mvc.perform(post("/api/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(race)))
                .andExpect(status().isCreated());

    }
}