package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.exception.ApplicationCodeNotValidException;
import es.codeurjc.mastercloudapps.your_race.model.*;
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



    public Long create(final RegistrationDTO registrationDTO) throws Exception {
        TrackDTO trackDTO = toTrackDTO(registrationDTO,TrackDTO.builder().build());

        if (trackDTO.getAthleteId()==null)
            throw new ApplicationCodeNotValidException();
        return trackService.create(trackDTO);
    }

    /*
    public Long createByOrder(final RegistrationByOrderDTO registrationByOrderDTO) throws Exception {
        TrackDTO trackDTO = toTrackDTO(registrationByOrderDTO,TrackDTO.builder().build());

       if (trackDTO.getAthleteId()==null)
           throw new ApplicationCodeNotValidException();
       return trackService.create(trackDTO);
    }
    public Long createByDraw(final RegistrationByDrawDTO registrationByDrawDTO) {
        return trackService.create(toTrackDTO(registrationByDrawDTO,TrackDTO.builder().build()));
    }
*/
    public List<TrackDTO> findAll() {
        return trackService.findAll();
    }


    private TrackDTO toTrackDTO(final RegistrationDTO registrationDTO, final TrackDTO trackDTO)
    {
        if (registrationDTO.getRegistrationType().equals(RegistrationType.BYDRAWING))
            return toTrackDTO((RegistrationByDrawDTO) registrationDTO,trackDTO);

        return toTrackDTO((RegistrationByOrderDTO) registrationDTO, trackDTO);


    }




    private TrackDTO toTrackDTO(final RegistrationByOrderDTO registrationByOrderDTO, final TrackDTO trackDTO){
       trackDTO.setRaceId(getRaceId(registrationByOrderDTO.getApplicationCode()).orElse(null));

       trackDTO.setAthleteId(
               applicationRepository.findAll().stream()
               .filter(application -> application.getApplicationCode().equals(registrationByOrderDTO.getApplicationCode()))
               .map(application -> application.getApplicationAthlete().getId())
               .findAny().orElse(null)

       );

       return trackDTO;

    }

    private TrackDTO toTrackDTO(final RegistrationByDrawDTO registrationByDrawDTO, final TrackDTO trackDTO){
        trackDTO.setAthleteId(registrationByDrawDTO.getIdAthlete());
        trackDTO.setRaceId(registrationByDrawDTO.getIdRace());

        return trackDTO;

    }

    private Optional<Long> getRaceId(String applicationCode){

       return applicationRepository.findAll().stream()
                .filter(application -> application.getApplicationCode().equals(applicationCode))
                .findAny()
                .map(application -> application.getApplicationRace().getId());

    }

}
