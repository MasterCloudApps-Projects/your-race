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
import java.util.List;
import java.util.stream.Collectors;
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
                .collect(Collectors.toList());
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
        raceDTO.setApplicationPeriod(race.getApplicationPeriod() == null ? null : race.getApplicationPeriod().getId());
        raceDTO.setOrganizer(race.getOrganizer() == null ? null : race.getOrganizer().getId());
        raceDTO.setRaceRegistration(race.getRaceRegistration() == null ? null : race.getRaceRegistration().getId());
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
        final ApplicationPeriod applicationPeriod = raceDTO.getApplicationPeriod() == null ? null : applicationPeriodRepository.findById(raceDTO.getApplicationPeriod())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "applicationPeriod not found"));
        race.setApplicationPeriod(applicationPeriod);
        final Organizer organizer = raceDTO.getOrganizer() == null ? null : organizerRepository.findById(raceDTO.getOrganizer())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "organizer not found"));
        race.setOrganizer(organizer);
        final Registration raceRegistration = raceDTO.getRaceRegistration() == null ? null : registrationRepository.findById(raceDTO.getRaceRegistration())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "raceRegistration not found"));
        race.setRaceRegistration(raceRegistration);
        return race;
    }

}
