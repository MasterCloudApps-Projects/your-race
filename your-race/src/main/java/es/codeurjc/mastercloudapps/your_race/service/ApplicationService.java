package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.Features;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Application;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Race;
import es.codeurjc.mastercloudapps.your_race.domain.exception.ApplicationPeriodIsClosedException;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationDTO;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationRequestDTO;
import es.codeurjc.mastercloudapps.your_race.repos.mongo.ApplicationMongoRepository;
import es.codeurjc.mastercloudapps.your_race.repos.mongo.AthleteMongoRepository;
import es.codeurjc.mastercloudapps.your_race.repos.mongo.RaceMongoRepository;
import es.codeurjc.mastercloudapps.your_race.repos.sql.ApplicationRepository;
import es.codeurjc.mastercloudapps.your_race.repos.sql.AthleteRepository;
import es.codeurjc.mastercloudapps.your_race.repos.sql.RaceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
public class ApplicationService {

   private final ApplicationRepository applicationRepository;
   private final AthleteRepository athleteRepository;
   private final RaceRepository raceRepository;
   private final FeatureManager featureManager;
   private final ApplicationMongoRepository applicationMongoRepository;
    private final AthleteMongoRepository athleteMongoRepository;
    private final RaceMongoRepository raceMongoRepository;

    public ApplicationService(ApplicationRepository applicationRepository, AthleteRepository athleteRepository, RaceRepository raceRepository, FeatureManager featureManager, ApplicationMongoRepository applicationMongoRepository, AthleteMongoRepository athleteMongoRepository, RaceMongoRepository raceMongoRepository) {
        this.applicationRepository = applicationRepository;
        this.athleteRepository = athleteRepository;
        this.raceRepository = raceRepository;
        this.featureManager = featureManager;
        this.applicationMongoRepository = applicationMongoRepository;
        this.athleteMongoRepository = athleteMongoRepository;
        this.raceMongoRepository = raceMongoRepository;
    }


    public Optional<ApplicationDTO> raceApplication (ApplicationRequestDTO applicationRequestDTO) throws ApplicationPeriodIsClosedException {

        if (this.featureManager.isActive(Features.USEMONGO))
            return raceMongoApplication(applicationRequestDTO);
        else
            return raceSQLApplication(applicationRequestDTO);

    }
    public Optional<ApplicationDTO> raceSQLApplication (ApplicationRequestDTO applicationRequestDTO) throws ApplicationPeriodIsClosedException {

        Optional<Athlete> athlete =  athleteRepository.findById(Long.valueOf(applicationRequestDTO.getAthleteId()));
        Optional<Race> race = raceRepository.findById(Long.valueOf(applicationRequestDTO.getRaceId()));

        if (athlete.isPresent() && race.isPresent()) {
            if (race.get().getApplicationPeriod().isOpen()) {
                Application application = Application.builder().applicationAthlete(athlete.get())
                        .applicationRace(race.get())
                        .applicationCode(getRandomCode())
                        .build();

                applicationRepository.save(application);
                return Optional.of(mapToDTO(application, new ApplicationDTO()));

            }
            throw new ApplicationPeriodIsClosedException("Application Period is closed");
        }
        return Optional.empty();


    }

    public Optional<ApplicationDTO> raceMongoApplication (ApplicationRequestDTO applicationRequestDTO) throws ApplicationPeriodIsClosedException {


        Optional<es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete> athlete =  athleteMongoRepository.findById(applicationRequestDTO.getAthleteId());
        Optional<es.codeurjc.mastercloudapps.your_race.domain.mongo.Race> race = raceMongoRepository.findById(applicationRequestDTO.getRaceId());

        if (athlete.isPresent() && race.isPresent()) {
            if (race.get().isOpenApplicationPeriod()) {
                es.codeurjc.mastercloudapps.your_race.domain.mongo.Application application =
                        es.codeurjc.mastercloudapps.your_race.domain.mongo.Application.builder().athlete(athlete.get())
                        .race(race.get())
                        .applicationCode(getRandomCode())
                        .build();

                applicationMongoRepository.save(application);
                return Optional.of(mapMongoToDTO(application, new ApplicationDTO()));

            }
            throw new ApplicationPeriodIsClosedException("Application Period is closed");
        }
        return Optional.empty();


    }


    private String getRandomCode(){

        String VALID_PW_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+{}[]|:;<>?,./";
        int DEFAULT_PASSWORD_LENGTH = 10;
        return RandomStringUtils.random(DEFAULT_PASSWORD_LENGTH, 0, VALID_PW_CHARS.length(), false,
                false, VALID_PW_CHARS.toCharArray(), new SecureRandom());
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
              .toList();
    }

    private ApplicationDTO mapToDTO(final Application application, final ApplicationDTO applicationDTO) {
        
        applicationDTO.setApplicationCode(application.getApplicationCode());

        applicationDTO.setName(application.getApplicationAthlete().getName());
        applicationDTO.setSurname(application.getApplicationAthlete().getSurname());

        applicationDTO.setRaceName(application.getApplicationRace().getName());
        /*applicationDTO.setDate(application.getApplicationRace().getDate());
        applicationDTO.setRaceRegistrationDate(application.getApplicationRace().getRaceRegistrationInfo().getRegistrationDate());
        */

        return applicationDTO;
    }


    private ApplicationDTO mapMongoToDTO(final es.codeurjc.mastercloudapps.your_race.domain.mongo.Application application, final ApplicationDTO applicationDTO) {

        applicationDTO.setApplicationCode(application.getApplicationCode());

        applicationDTO.setName(application.getAthlete().getName());
        applicationDTO.setSurname(application.getAthlete().getSurname());

        applicationDTO.setRaceName(application.getRace().getName());
        /*applicationDTO.setDate(application.getApplicationRace().getDate());
        applicationDTO.setRaceRegistrationDate(application.getApplicationRace().getRaceRegistrationInfo().getRegistrationDate());
        */

        return applicationDTO;
    }

}
