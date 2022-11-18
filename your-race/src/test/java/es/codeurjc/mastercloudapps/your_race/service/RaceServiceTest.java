package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.model.RaceDTO;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import es.codeurjc.mastercloudapps.your_race.repos.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RaceServiceTest {

    @Mock
    private RaceRepository raceRepository;
    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private RaceService raceService;
    
    
    @Test
    void get() {
        given(raceRepository.findById(anyLong())).willReturn(Optional.of(Race.builder()
                .name("Pepito")
                .build()));
        given(trackRepository.countByRace(any(Race.class))).willReturn(1);
        
        // when -  action or the behaviour that we are going test
        RaceDTO raceDTO = raceService.get(1L);

        // then - verify the output
        assertThat(raceDTO.getName()).isEqualTo("Pepito");
    }
}