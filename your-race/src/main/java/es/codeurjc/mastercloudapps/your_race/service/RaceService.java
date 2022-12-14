package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.ApplicationPeriod;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.RegistrationInfo;
import es.codeurjc.mastercloudapps.your_race.model.RaceDTO;
import es.codeurjc.mastercloudapps.your_race.model.RaceStatus;
import es.codeurjc.mastercloudapps.your_race.repos.OrganizerRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import es.codeurjc.mastercloudapps.your_race.repos.TrackRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class RaceService {

    private final RaceRepository raceRepository;
    private final OrganizerRepository organizerRepository;
    private final TrackRepository trackRepository;


    public RaceService(final RaceRepository raceRepository,
                       final OrganizerRepository organizerRepository,
                       final TrackRepository trackRepository) {
        this.raceRepository = raceRepository;
        this.organizerRepository = organizerRepository;
        this.trackRepository = trackRepository;
    }

    public List<RaceDTO> findAll() {
        return raceRepository.findAll(Sort.by("id"))
                .stream()
                .map(race -> mapToDTO(race, new RaceDTO()))
                .toList();
    }

    public List<RaceDTO> findOpenRaces() {
       return raceRepository.findAll(Sort.by("id"))
                .stream()
                .filter(Race::isOpen)
                .map(race -> mapToDTO(race, new RaceDTO()))
                .toList();
    }

    public RaceDTO get(final Long id) {
        return raceRepository.findById(id)
                .map(race -> mapToDTO(race, new RaceDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public int getCapacity(final Race race) {
        return trackRepository.countByRace(race);
    }

    public Long create(final RaceDTO raceDTO) {
        final Race race = new Race();
        mapToEntity(raceDTO, race);
        race.setRaceStatus(RaceStatus.PRE_REGISTRATION_OPEN);
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


        raceDTO.setRaceRegistrationDate (race.getRaceRegistrationInfo() == null ? null : race.getRaceRegistrationInfo().getRegistrationDate());
        raceDTO.setRegistrationType (race.getRaceRegistrationInfo() == null ? null : race.getRaceRegistrationInfo().getRegistrationType());
        raceDTO.setRegistrationCost (race.getRaceRegistrationInfo() == null ? null : race.getRaceRegistrationInfo().getRegistrationCost());
        raceDTO.setAvailableCapacity(getCapacity(race));
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

        final RegistrationInfo raceRegistrationInfo = raceDTO.getRaceRegistrationDate() == null ? null :
                RegistrationInfo.builder().registrationDate(raceDTO.getRaceRegistrationDate())
                                .registrationType(raceDTO.getRegistrationType())
                                .registrationCost(raceDTO.getRegistrationCost())
                                        .build();
        race.setRaceRegistrationInfo(raceRegistrationInfo);
        return race;
    }


    private Optional<Organizer> findOrganizer(String name){

        return organizerRepository.findAll()
                .stream()
                .filter(organizer -> organizer.getName().equals(name))
                .findAny();

    }
}
