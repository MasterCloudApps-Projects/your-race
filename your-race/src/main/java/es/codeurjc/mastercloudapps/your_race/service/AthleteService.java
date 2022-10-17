package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Application;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationDTO;
import es.codeurjc.mastercloudapps.your_race.model.AthleteDTO;
import es.codeurjc.mastercloudapps.your_race.model.RaceDTO;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;
import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Transactional
@Service
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private final ApplicationRepository applicationRepository;
    private final RaceRepository raceRepository;

    public AthleteService(final AthleteRepository athleteRepository, final ApplicationRepository applicationRepository
                        ,RaceRepository raceRepository) {
        this.athleteRepository = athleteRepository;
        this.applicationRepository = applicationRepository;
        this.raceRepository = raceRepository;
    }

    public List<AthleteDTO> findAll() {
        return athleteRepository.findAll(Sort.by("id"))
                .stream()
                .map(athlete -> mapToDTO(athlete, new AthleteDTO()))
                .toList();
    }

    public AthleteDTO get(final Long id) {
        return athleteRepository.findById(id)
                .map(athlete -> mapToDTO(athlete, new AthleteDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final AthleteDTO athleteDTO) {
        final Athlete athlete = new Athlete();
        mapToEntity(athleteDTO, athlete);
        return athleteRepository.save(athlete).getId();
    }

    public Optional<ApplicationDTO> raceApplication( final Long idAthlete, final Long idRace){

        Optional<Athlete> athlete = athleteRepository.findById(idAthlete);
        Optional<Race> race = raceRepository.findById(idRace);


        if (athlete.isPresent() && race.isPresent()) {
            Application application = Application.builder().applicationAthlete(athlete.get())
                    .applicationRace(race.get())
                    .applicationCode(RandomStringUtils.random(10,true,true))
                    .build();

            applicationRepository.save(application);
            return Optional.of(mapToDTO(application, new ApplicationDTO()));

        }
        else
            return Optional.empty();


    }


    public List<ApplicationDTO> findAllApplications(Long id){

        return  applicationRepository.findAll()
                .stream()
                .filter(application -> Objects.equals(application.getApplicationAthlete().getId(), id))
                .map(application -> mapToDTO(application, new ApplicationDTO()))
                .toList();
    }

    public List<ApplicationDTO> findAllApplicationsOpenRaces(Long id){

        List<ApplicationDTO> applicationDTOs = applicationRepository.findAll()
                .stream()
                .filter(application -> Objects.equals(application.getApplicationAthlete().getId(), id))
                .filter(application -> LocalDateTime.now().isBefore(application.getApplicationRace().getDate()))
                .map(application -> mapToDTO(application, new ApplicationDTO()))
                .toList();

        return applicationDTOs;
    }


    public void update(final Long id, final AthleteDTO athleteDTO) {
        final Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(athleteDTO, athlete);
        athleteRepository.save(athlete);
    }

    public void delete(final Long id) {
        athleteRepository.deleteById(id);
    }

    private AthleteDTO mapToDTO(final Athlete athlete, final AthleteDTO athleteDTO) {
        athleteDTO.setId(athlete.getId());
        athleteDTO.setName(athlete.getName());
        athleteDTO.setSurname(athlete.getSurname());
        athleteDTO.setTrackRecord(athlete.getTrackRecord());
        return athleteDTO;
    }

    private Athlete mapToEntity(final AthleteDTO athleteDTO, final Athlete athlete) {
        athlete.setName(athleteDTO.getName());
        athlete.setSurname(athleteDTO.getSurname());
        athlete.setTrackRecord(athleteDTO.getTrackRecord());
        return athlete;
    }

    private ApplicationDTO mapToDTO(final Application application, final ApplicationDTO applicationDTO) {

        applicationDTO.setApplicationCode(application.getApplicationCode());
        applicationDTO.setName(application.getApplicationAthlete().getName());
        applicationDTO.setSurname(application.getApplicationAthlete().getSurname());
        applicationDTO.setRaceName(application.getApplicationRace().getName());
        applicationDTO.setDate(application.getApplicationRace().getDate());
        applicationDTO.setRaceRegistrationDate(application.getApplicationRace().getRaceRegistration().getRegistrationDate());


        return applicationDTO;
    }


}
