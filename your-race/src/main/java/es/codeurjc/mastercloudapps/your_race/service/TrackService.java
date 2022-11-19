package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.Features;
import es.codeurjc.mastercloudapps.your_race.domain.Application;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import es.codeurjc.mastercloudapps.your_race.domain.exception.ApplicationCodeNotValidException;
import es.codeurjc.mastercloudapps.your_race.domain.exception.AthleteAlreadyRegisteredToRace;
import es.codeurjc.mastercloudapps.your_race.domain.exception.RaceFullCapacityException;
import es.codeurjc.mastercloudapps.your_race.domain.exception.notfound.AthleteNotFoundException;
import es.codeurjc.mastercloudapps.your_race.domain.exception.notfound.RaceNotFoundException;
import es.codeurjc.mastercloudapps.your_race.domain.exception.notfound.YourRaceNotFoundException;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationByDrawDTO;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationByOrderDTO;
import es.codeurjc.mastercloudapps.your_race.model.TrackDTO;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;
import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import es.codeurjc.mastercloudapps.your_race.repos.TrackRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.togglz.core.manager.FeatureManager;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final RaceRepository raceRepository;
    private final AthleteRepository athleteRepository;
    private final ApplicationRepository applicationRepository;
    private final FeatureManager featureManager;


    public TrackService(final TrackRepository trackRepository, final RaceRepository raceRepository,
                        final AthleteRepository athleteRepository, final ApplicationRepository applicationRepository,
                        final FeatureManager featureManager) {
        this.trackRepository = trackRepository;
        this.raceRepository = raceRepository;
        this.athleteRepository = athleteRepository;
        this.applicationRepository = applicationRepository;
        this.featureManager = featureManager;
    }

    public List<TrackDTO> findAll() {
        return trackRepository.findAll(Sort.by("id"))
                .stream()
                .map(track -> mapToDTO(track, new TrackDTO()))
                .toList();
    }

    public TrackDTO get(final Long id) {
        return trackRepository.findById(id)
                .map(track -> mapToDTO(track, new TrackDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @CircuitBreaker(name = "CBfindByApplicationCode")
    public Optional<Application> findByApplicationCode(final RegistrationByOrderDTO registrationByOrderDTO){
        return applicationRepository.findByApplicationCode(registrationByOrderDTO.getApplicationCode());
    }
    
    public TrackDTO createByOrder(final RegistrationByOrderDTO registrationByOrderDTO) throws ApplicationCodeNotValidException, RaceFullCapacityException, AthleteAlreadyRegisteredToRace {
        Optional<Application> application;

        if(featureManager.isActive(Features.usecb)) {
            application = findByApplicationCode(registrationByOrderDTO);
        } else {
            application = applicationRepository.findByApplicationCode(registrationByOrderDTO.getApplicationCode());
        }
        
        if (!application.isPresent())
            throw new ApplicationCodeNotValidException("Application code is invalid. ApplicationCode was not found.");

        return registerToRace(application.get().getApplicationAthlete(),application.get().getApplicationRace());

    }
    public TrackDTO createByDraw(final RegistrationByDrawDTO registrationByDrawDTO) throws RaceFullCapacityException, YourRaceNotFoundException, AthleteAlreadyRegisteredToRace {
        Race race = getRace(registrationByDrawDTO);
        Athlete athlete = getAthlete(registrationByDrawDTO);

        if (race== null)
            throw new RaceNotFoundException("Race not found.");
        if (athlete==null)
           throw new AthleteNotFoundException("Athlete not found.");


        return registerToRace(athlete,race);


    }

    private TrackDTO registerToRace(Athlete athlete, Race race) throws RaceFullCapacityException, AthleteAlreadyRegisteredToRace {

        if(findAthleteTrackInRace(athlete,race))
            throw new AthleteAlreadyRegisteredToRace("Athlete already registered to race. Race: " + race.getName() + ". Athlete: " + athlete.getName());

        Track track = trackRepository.save(Track.builder()
                .race(race)
                .athlete(athlete)
                .dorsal(race.getNextDorsal(trackRepository.countByRace(race)))
                .registrationDate(LocalDateTime.now())
                .paymentInfo("Pending")
                .status("Registered")
                .build());
        
        return mapToDTO(track, new TrackDTO());
    }

    private boolean findAthleteTrackInRace(Athlete athlete, Race race){
       Optional<Track> optionalTrack = trackRepository.findByAthleteAndRace(athlete,race);

        return optionalTrack.isPresent();
    }


    public void update(final Long id, final TrackDTO trackDTO) {
        final Track track = trackRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(trackDTO, track);
        trackRepository.save(track);
    }

    public void delete(final Long id) {
        trackRepository.deleteById(id);
    }


    public List<TrackDTO> findAllByAthlete(Long id){

        return  trackRepository.findAll()
                .stream()
                .filter(track -> track.getAthlete().getId().equals(id))
                .map(track -> mapToDTO(track,new TrackDTO()))
                .toList();

    }

    public List<TrackDTO> findAllOpenByAthlete(Long id){

        return  trackRepository.findAll()
                .stream()
                .filter(track -> track.getAthlete().getId().equals(id))
                .filter(track -> track.getRace().isOpen())
                .map(track -> mapToDTO(track,new TrackDTO()))
                .toList();
    }

    public List<TrackDTO> findAllByRace(Long id){

        return  trackRepository.findAll()
                .stream()
                .filter(track -> track.getRace().getId().equals(id))
                .map(track -> mapToDTO(track,new TrackDTO()))
                .toList();
    }

    public List<TrackDTO> findByAthleteAndRace(Long athleteId,Long raceId){

        return  trackRepository.findAll()
                .stream()
                .filter(track -> track.getAthlete().getId().equals(athleteId))
                .filter(track -> track.getRace().getId().equals(raceId))
                .map(track -> mapToDTO(track,new TrackDTO()))
                .toList();
    }

    private TrackDTO mapToDTO(final Track track, final TrackDTO trackDTO) {
        trackDTO.setId(track.getId());
        trackDTO.setAthleteId(track.getAthlete() == null ? null : track.getAthlete().getId());
        trackDTO.setName(track.getAthlete() == null ? null : track.getAthlete().getName());
        trackDTO.setSurname(track.getAthlete() == null ? null : track.getAthlete().getSurname());

        trackDTO.setRaceId(track.getRace() == null ? null : track.getRace().getId());
        trackDTO.setRaceName(track.getRace() == null ? null : track.getRace().getName());
        /*trackDTO.setRaceDate(track.getRace() == null ? null : track.getRace().getDate());
        trackDTO.setRegistrationDate(track.getRegistrationDate());*/

        trackDTO.setStatus(track.getStatus());
        trackDTO.setScore(track.getScore());
        trackDTO.setDorsal(track.getDorsal());
        trackDTO.setPaymentInfo(track.getPaymentInfo());

        return trackDTO;
    }

    private Track mapToEntity(final TrackDTO trackDTO, final Track track) {
        track.setRegistrationDate(trackDTO.getRegistrationDate());
        track.setStatus(trackDTO.getStatus());
        track.setScore(trackDTO.getScore());
        track.setDorsal(trackDTO.getDorsal());
        track.setPaymentInfo(trackDTO.getPaymentInfo());
        final Race race = trackDTO.getRaceId() == null ? null : raceRepository.findById(trackDTO.getRaceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "race not found"));

        track.setRace(race);
        final Athlete athlete = trackDTO.getAthleteId() == null ? null : athleteRepository.findById(trackDTO.getAthleteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "athlete not found"));
        track.setAthlete(athlete);
        return track;
    }

    private Race getRace(RegistrationByDrawDTO registrationByDrawDTO){

        return applicationRepository.findAll().stream()
                .filter(application -> application.getApplicationRace().getId().equals(registrationByDrawDTO.getRaceId()))
                .findAny()
                .map(Application::getApplicationRace).orElse(null);

    }

    private Athlete getAthlete(RegistrationByDrawDTO registrationByDrawDTO){

        return applicationRepository.findAll().stream()
                .filter(application -> application.getApplicationAthlete().getId().equals(registrationByDrawDTO.getAthleteId()))
                .findAny()
                .map(Application::getApplicationAthlete).orElse(null);

    }


}

