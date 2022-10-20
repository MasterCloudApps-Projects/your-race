package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Application;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationDTO;
import es.codeurjc.mastercloudapps.your_race.model.TrackDTO;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private final TrackService trackService;
    private final ApplicationRepository applicationRepository;

    public RegistrationService(TrackService trackService, ApplicationRepository applicationRepository){
        this.trackService = trackService;
        this.applicationRepository = applicationRepository;
    }

    public Long create(final RegistrationDTO registrationDTO) {
       return trackService.create(toTrackDTO(registrationDTO,TrackDTO.builder().build()));
    }

    public List<TrackDTO> findAll() {
        return trackService.findAll();
    }


    private TrackDTO toTrackDTO(final RegistrationDTO registrationDTO, final TrackDTO trackDTO){
       trackDTO.setAthleteId(registrationDTO.getIdAthlete());
       trackDTO.setRaceId(getRaceId(registrationDTO.getApplicationCode()).orElse(null));

       return trackDTO;

    }

    private Optional<Long> getRaceId(String applicationCode){

       return applicationRepository.findAll().stream()
                .filter(application -> application.getApplicationCode().equals(applicationCode))
                .findAny()
                .map(application -> application.getApplicationRace().getId());

    }

}
