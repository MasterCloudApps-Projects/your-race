package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Application;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationDTO;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ApplicationService {

   private final ApplicationRepository applicationRepository;


    public ApplicationService(final ApplicationRepository applicationRepository) {

        this.applicationRepository = applicationRepository;
    }

    public List<ApplicationDTO> getRaceApplications(final Long id) {

       return  applicationRepository.findAll().stream()
                .filter(application -> application.getApplicationRace().getId().equals(id))
                .map(application ->  mapToDTO(application, new ApplicationDTO()))
               .collect(Collectors.toList());



    }

    private ApplicationDTO mapToDTO(final Application application, final ApplicationDTO applicationDTO) {

       applicationDTO.setApplicationCode(application.getApplicationCode());

        applicationDTO.setName(application.getApplicationAthlete().getName());
        applicationDTO.setSurname(application.getApplicationAthlete().getSurname());

        applicationDTO.setRaceName(application.getApplicationRace().getName());
       // applicationDTO.setDate(application.getApplicationRace().getDate());

     //   applicationDTO.setRaceRegistrationDate(application.getApplicationRace().getRaceRegistrationInfo().getRegistrationDate());


        return applicationDTO;
    }

}
