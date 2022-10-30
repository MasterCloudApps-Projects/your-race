package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Application;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.exception.ApplicationPeriodIsClosedException;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationDTO;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationRequestDTO;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;

import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ApplicationService {

   private final ApplicationRepository applicationRepository;
   private final AthleteRepository athleteRepository;
   private final RaceRepository raceRepository;

    public ApplicationService(ApplicationRepository applicationRepository, AthleteRepository athleteRepository, RaceRepository raceRepository) {
        this.applicationRepository = applicationRepository;
        this.athleteRepository = athleteRepository;
        this.raceRepository = raceRepository;
    }

    public Optional<ApplicationDTO> raceApplication (ApplicationRequestDTO applicationRequestDTO) throws Exception {


        Optional<Athlete> athlete =  athleteRepository.findById(applicationRequestDTO.getAthleteId());
        Optional<Race> race = raceRepository.findById(applicationRequestDTO.getRaceId());


        if (athlete.isPresent() && race.isPresent()) {
            if (race.get().getApplicationPeriod().isOpen()) {
                Application application = Application.builder().applicationAthlete(athlete.get())
                        .applicationRace(race.get())
                        .applicationCode(RandomStringUtils.random(10, true, true))
                        .build();

                applicationRepository.save(application);
                return Optional.of(mapToDTO(application, new ApplicationDTO()));

            }
            throw new ApplicationPeriodIsClosedException("Application Period is closed");
        }
        return Optional.empty();


    }

    public List<ApplicationDTO> findAllApplication(Long id){

        return  applicationRepository.findAll()
                .stream()
                .filter(application -> Objects.equals(application.getApplicationAthlete().getId(), id))
                .map(application -> mapToDTO(application, new ApplicationDTO()))
                .toList();
    }

    public List<ApplicationDTO> findAllApplicationOpenRace(Long id){

        return applicationRepository.findAll()
                .stream()
                .filter(application -> Objects.equals(application.getApplicationAthlete().getId(), id))
                .filter(application ->  application.getApplicationRace().isOpen())
                .map(application -> mapToDTO(application, new ApplicationDTO()))
                .toList();

    }


    public List<ApplicationDTO> getRaceApplications(final Long id) {
        return applicationRepository.findAll().stream()
               .filter(application -> application.getApplicationRace().getId().equals(id))
               .map(application ->  mapToDTO(application, new ApplicationDTO()))
              .collect(Collectors.toList());
    }

    private ApplicationDTO mapToDTO(final Application application, final ApplicationDTO applicationDTO) {

       applicationDTO.setApplicationCode(application.getApplicationCode());

        applicationDTO.setName(application.getApplicationAthlete().getName());
        applicationDTO.setSurname(application.getApplicationAthlete().getSurname());

        applicationDTO.setRaceName(application.getApplicationRace().getName());
        applicationDTO.setDate(application.getApplicationRace().getDate());
        applicationDTO.setRaceRegistrationDate(application.getApplicationRace().getRaceRegistrationInfo().getRegistrationDate());


        return applicationDTO;
    }

}
