package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.model.AthleteDTO;
import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AthleteServiceTest {

    @Mock
    private AthleteRepository athleteRepository;

    @InjectMocks
    private AthleteService athleteService;
    private Athlete savedAthlete;

    private AthleteDTO athleteDTO;

    @BeforeEach
    public void setup(){

        savedAthlete = Athlete.builder()
                .id(1L)
                .name("Pepito")
                .surname("Grillo")
                .build();
        athleteDTO = AthleteDTO.builder()
                .name("Pepito")
                .surname("Grillo")
                .build();
    }

    @DisplayName("Create new athlete")
    @Test
    void createTest() throws Exception{
        

        given(athleteRepository.save(any(Athlete.class))).willReturn(savedAthlete);

        System.out.println(athleteRepository);
        System.out.println(athleteService);

        // when -  action or the behaviour that we are going test
        Long id = athleteService.create(athleteDTO);
        
        // then - verify the output
        assertThat(id).isNotNull();

    }
}