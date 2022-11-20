package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Application;
import es.codeurjc.mastercloudapps.your_race.domain.exception.ApplicationCodeNotValidException;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationByOrderDTO;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {
    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationService applicationService;


    @Test
    void findByApplicationCode() throws ApplicationCodeNotValidException {
        given(applicationRepository.findByApplicationCode(any(String.class))).willReturn(Optional.of(Application.builder()
                .id(1L)
                .build()));

        // when -  action or the behaviour that we are going test
        Application application = applicationService.findByApplicationCode(RegistrationByOrderDTO.builder()
                .applicationCode("")
                .build());

        // then - verify the output
        assertThat(application.getId()).isEqualTo(1L);
    }
}