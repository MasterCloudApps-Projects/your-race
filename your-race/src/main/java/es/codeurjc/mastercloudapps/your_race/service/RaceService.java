package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.ApplicationPeriod;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Registration;
import es.codeurjc.mastercloudapps.your_race.model.RaceDTO;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationPeriodRepository;
import es.codeurjc.mastercloudapps.your_race.repos.OrganizerRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RegistrationRepository;

import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class RaceService {

    private final RaceRepository raceRepository;
    private final ApplicationPeriodRepository applicationPeriodRepository;
    private final OrganizerRepository organizerRepository;
    private final RegistrationRepository registrationRepository;

    public RaceService(final RaceRepository raceRepository,
            final ApplicationPeriodRepository applicationPeriodRepository,
            final OrganizerRepository organizerRepository,
            final RegistrationRepository registrationRepository) {
        this.raceRepository = raceRepository;
        this.applicationPeriodRepository = applicationPeriodRepository;
        this.organizerRepository = organizerRepository;
        this.registrationRepository = registrationRepository;


    }

    public List<RaceDTO> findAll() {
        return raceRepository.findAll(Sort.by("id"))
                .stream()
                .map(race -> mapToDTO(race, new RaceDTO()))
                .toList();
    }

    public List<RaceDTO> findOpenRaces() {

        List<RaceDTO> openRacesDTO = new ArrayList<RaceDTO>();
        List<RaceDTO> allRacesDTO =  raceRepository.findAll(Sort.by("id"))
                .stream()
                .map(race -> mapToDTO(race, new RaceDTO()))
                .toList();
        for (RaceDTO raceDTO : allRacesDTO) {
           if (LocalDateTime.now().isBefore(raceDTO.getDate()))
                openRacesDTO.add(raceDTO);
        }

        return openRacesDTO;
    }

    public RaceDTO get(final Long id) {
        return raceRepository.findById(id)
                .map(race -> mapToDTO(race, new RaceDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final RaceDTO raceDTO) {
        final Race race = new Race();
        mapToEntity(raceDTO, race);
        return raceRepository.save(race).getId();
    }

    public void update(final Long id, final RaceDTO raceDTO) {
        final Race race = raceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(raceDTO, race);
        raceRepository.save(race);
    }

    public void delete(final Long id) {
        raceRepository.deleteById(id);
    }

    private RaceDTO mapToDTO(final Race race, final RaceDTO raceDTO) {
        raceDTO.setId(race.getId());
        raceDTO.setName(race.getName());
        raceDTO.setDescription(race.getDescription());
        raceDTO.setDate(race.getDate());
        raceDTO.setLocation(race.getLocation());
        raceDTO.setDistance(race.getDistance());
        raceDTO.setType(race.getType());
        raceDTO.setAthleteCapacity(race.getAthleteCapacity());
        raceDTO.setApplicationInitialDate(race.getApplicationPeriod()==null ? null : race.getApplicationPeriod().getInitialDate());
        raceDTO.setApplicationLastDate(race.getApplicationPeriod()==null ? null : race.getApplicationPeriod().getLastDate());

        raceDTO.setOrganizerName(race.getOrganizer() == null ? null : race.getOrganizer().getName());


        raceDTO.setRaceRegistrationDate (race.getRaceRegistration() == null ? null : race.getRaceRegistration().getRegistrationDate());
        raceDTO.setRegistrationType (race.getRaceRegistration() == null ? null : race.getRaceRegistration().getRegistrationType());
        raceDTO.setRegistrationCost (race.getRaceRegistration() == null ? null : race.getRaceRegistration().getRegistrationCost());
        return raceDTO;
    }

    private Race mapToEntity(final RaceDTO raceDTO, final Race race) {
        race.setName(raceDTO.getName());
        race.setDescription(raceDTO.getDescription());
        race.setDate(raceDTO.getDate());
        race.setLocation(raceDTO.getLocation());
        race.setDistance(raceDTO.getDistance());
        race.setType(raceDTO.getType());
        race.setAthleteCapacity(raceDTO.getAthleteCapacity());

        final ApplicationPeriod applicationPeriod =  raceDTO.getApplicationInitialDate()== null ? null :
                ApplicationPeriod.builder().initialDate(raceDTO.getApplicationInitialDate())
                                .lastDate(raceDTO.getApplicationLastDate()).build();

        race.setApplicationPeriod(applicationPeriod);


        final Organizer organizer = raceDTO.getOrganizerName() == null ? null :
                findOrganizer(raceDTO.getOrganizerName())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "organizer not found"));
        race.setOrganizer(organizer);

        final Registration raceRegistration = raceDTO.getRaceRegistrationDate() == null ? null :
                Registration.builder().registrationDate(raceDTO.getRaceRegistrationDate())
                                .registrationType(raceDTO.getRegistrationType())
                                .registrationCost(raceDTO.getRegistrationCost())
                                        .build();
        race.setRaceRegistration(raceRegistration);
        return race;
    }


    private Optional<Organizer> findOrganizer(String name){
        List<Organizer> organizers = organizerRepository.findAll();
        for(Organizer organizer : organizers)
        {
            if(organizer.getName().equals(name))
                return Optional.of(organizer);
        }
        return Optional.empty();
    }





}
