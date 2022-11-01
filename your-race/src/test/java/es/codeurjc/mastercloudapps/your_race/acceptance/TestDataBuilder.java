package es.codeurjc.mastercloudapps.your_race.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import es.codeurjc.mastercloudapps.your_race.domain.*;
import es.codeurjc.mastercloudapps.your_race.model.*;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TestDataBuilder {

        private static Faker faker;
        private static ObjectMapper mapper;

        static void init(){
            faker = new Faker();
            mapper = new ObjectMapper();
        }


        static Race buildTestRace(Organizer organizer) {
            return Race.builder()
                    .name(faker.esports().event())
                    .description("A race for testing")
                    .location(faker.address().cityName())
                    .distance(faker.number().randomDouble(2, 0, 1000))
                    .organizer(organizer)
                    .raceRegistrationInfo(RegistrationInfo.builder()
                            .registrationDate(LocalDateTime.now().plusMonths(4L))
                            .build())
                    .applicationPeriod(ApplicationPeriod.builder()
                            .initialDate(LocalDateTime.now().minusMonths(1L))
                            .lastDate(LocalDateTime.now().plusMonths(1L))
                            .build())
                    .date(LocalDateTime.now().plusMonths(6L))
                    .athleteCapacity(3)
                    .build();
        }

        static void setDateInFuture(Race race) {

            LocalDateTime date = LocalDateTime.now().plusMonths(4L);
            race.setDate(LocalDateTime.of(date.getYear(), date.getMonth(), 1, 9, 0));

        }

        static void setDateInPast(Race race) {

            LocalDateTime date = LocalDateTime.now().minusMonths(1L);
            race.setDate(LocalDateTime.of(date.getYear(), date.getMonth(), 1, 9, 0));

        }

        static Athlete buildTestAthlete() {
            return Athlete.builder().name(faker.name().firstName()).surname(faker.name().lastName()).build();
        }
        static Organizer buildTestOrganizer(){
            return Organizer.builder().name("Test Organizer").build();
        }

        static Track buildTrack(Athlete athlete, Race race){
            return Track.builder()
                    .athlete(athlete)
                    .race(race)
                    .status(faker.options().option("REGISTERED","PARTICIPATED"))
                    .registrationDate(race.getRaceRegistrationInfo().getRegistrationDate())
                    .dorsal(RandomUtils.nextInt())
                    .build();
        }
        static void setApplicationPeriodOpen(Race race){
            race.getApplicationPeriod().setInitialDate(LocalDateTime.now().minusMonths(1L));
            race.getApplicationPeriod().setLastDate(LocalDateTime.now().plusMonths(2));
        }

        static void setApplicationPeriodClosed(Race race){
            race.getApplicationPeriod().setInitialDate(LocalDateTime.now().minusMonths(3L));
            race.getApplicationPeriod().setLastDate(LocalDateTime.now().minusMonths(2));
        }

    public static String generateRegistrationByOrderBodyRequest(ApplicationDTO applicationDTO) throws Exception{
         return mapper.writeValueAsString(produceRegistrationByOrder(applicationDTO));
    }

    public static String generateRegistrationByDrawBodyRequest (Athlete athlete, Race race) throws Exception{
        return mapper.writeValueAsString(produceRegistrationByDraw(athlete,race));

    }
    private static RegistrationByOrderDTO produceRegistrationByOrder(ApplicationDTO applicationDTO){
        return RegistrationByOrderDTO.builder()
                .applicationCode(applicationDTO.getApplicationCode())
                .build();
    }

    private static RegistrationByDrawDTO produceRegistrationByDraw(Athlete athlete, Race race){
        return RegistrationByDrawDTO.builder()
                .athleteId(athlete.getId())
                .raceId(race.getId())
                .build();

    }


    public static ApplicationDTO athleteApplyToRace(MockMvc mvc, Athlete athlete, Race race) throws Exception{
       String  request = mapper.writeValueAsString(ApplicationRequestDTO.builder()
                .athleteId(athlete.getId())
                .raceId(race.getId()).build());


       MvcResult result =  mvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationCode").isNotEmpty()).andReturn();

        return mapper.readValue( result.getResponse().getContentAsString(), ApplicationDTO.class);

    }

    public static TrackDTO registerAthleteToRaceByOrder(MockMvc mvc, Athlete athlete, Race race) throws Exception{

        ApplicationDTO applicationDTO = athleteApplyToRace(mvc, athlete,race);

        MvcResult result = mvc.perform(post("/api/tracks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(generateRegistrationByOrderBodyRequest(applicationDTO)))
                .andExpect(status().isCreated()).andReturn();

        return mapper.readValue(result.getResponse().getContentAsString(), TrackDTO.class);



    }
    }

