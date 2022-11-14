package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Application;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import es.codeurjc.mastercloudapps.your_race.model.RaceDTO;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationByOrderDTO;
import es.codeurjc.mastercloudapps.your_race.model.TrackDTO;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import es.codeurjc.mastercloudapps.your_race.repos.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @Mock
    private TrackRepository trackRepository;
    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private TrackService trackService;


    @Test
    void get() {
        given(trackRepository.findById(anyLong())).willReturn(Optional.of(Track.builder()
                .id(1L)
                .build()));

        // when -  action or the behaviour that we are going test
        TrackDTO trackDTO = trackService.get(1L);

        // then - verify the output
        assertThat(trackDTO.getId()).isEqualTo(1L);
    }

    @Test
    void findAll() {
        given(trackRepository.findAll(Sort.by("id"))).willReturn(List.of(Track.builder()
                .id(1L)
                .build()));

        // when -  action or the behaviour that we are going test
        List<TrackDTO> trackDTOList = trackService.findAll();

        // then - verify the output
        assertThat(trackDTOList.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void findByApplicationCode() {
        given(applicationRepository.findByApplicationCode(any(String.class))).willReturn(Optional.of(Application.builder()
                .id(1L)
                .build()));

        // when -  action or the behaviour that we are going test
        Optional<Application> application = trackService.findByApplicationCode(RegistrationByOrderDTO.builder()
                .applicationCode("")
                .build());

        // then - verify the output
        assertThat(application).isNotEmpty();
    }

    
}