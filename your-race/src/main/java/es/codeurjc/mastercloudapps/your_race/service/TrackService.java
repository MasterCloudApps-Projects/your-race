package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Application;
import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import es.codeurjc.mastercloudapps.your_race.domain.exception.ApplicationCodeNotValidException;
import es.codeurjc.mastercloudapps.your_race.domain.exception.RaceCapacityIsEmpty;
import es.codeurjc.mastercloudapps.your_race.model.*;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;
import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import es.codeurjc.mastercloudapps.your_race.repos.TrackRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;


@Transactional
@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final RaceRepository raceRepository;
    private final AthleteRepository athleteRepository;

    private final ApplicationRepository applicationRepository;


    public TrackService(final TrackRepository trackRepository, final RaceRepository raceRepository,
                        final AthleteRepository athleteRepository, final ApplicationRepository applicationRepository) {
        this.trackRepository = trackRepository;
        this.raceRepository = raceRepository;
        this.athleteRepository = athleteRepository;

        this.applicationRepository = applicationRepository;
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



    public TrackDTO create(final RegistrationDTO registrationDTO) throws ApplicationCodeNotValidException, RaceCapacityIsEmpty {
    Race race = getRegistrationRace(registrationDTO);
    Athlete athlete = getRegistrationAthlete(registrationDTO);

        if (athlete==null)
            throw new ApplicationCodeNotValidException("Application code is invalid. Athlete was not found.");

        if (race==null)
            throw new ApplicationCodeNotValidException("Application code is invalid. Race was not found.");

        int dorsal = race.getNextDorsal();

        Track track = Track.builder()
                .race(race)
                .athlete(athlete)
                .dorsal(dorsal)
                .registrationDate(LocalDateTime.now())
                .paymentInfo("Pending")
                .status("Registered")
                .build();
        track = trackRepository.save(track);
        return mapToDTO(track, new TrackDTO());

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



    private TrackDTO mapToDTO(final Track track, final TrackDTO trackDTO) {
        trackDTO.setId(track.getId());
        trackDTO.setAthleteId(track.getAthlete() == null ? null : track.getAthlete().getId());
        trackDTO.setName(track.getAthlete() == null ? null : track.getAthlete().getName());
        trackDTO.setSurname(track.getAthlete() == null ? null : track.getAthlete().getSurname());

        trackDTO.setRaceId(track.getRace() == null ? null : track.getRace().getId());
        trackDTO.setRaceName(track.getRace() == null ? null : track.getRace().getName());
       // trackDTO.setRaceDate(track.getRace() == null ? null : track.getRace().getDate());

      // trackDTO.setRegistrationDate(track.getRegistrationDate());

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


    private Race getRegistrationRace(final RegistrationDTO registrationDTO)
    {
       if(registrationDTO.getClass().equals(RegistrationByOrderDTO.class))
            return getRace((RegistrationByOrderDTO) registrationDTO).orElse(null);
       return  getRace((RegistrationByDrawDTO) registrationDTO).orElse(null);

    }



    private Athlete getRegistrationAthlete(final RegistrationDTO registrationDTO)
    {
        if(registrationDTO.getClass().equals(RegistrationByOrderDTO.class))
            return getAthlete((RegistrationByOrderDTO) registrationDTO).orElse(null);
        return  getAthlete((RegistrationByDrawDTO) registrationDTO).orElse(null);


    }



    private Optional<Race> getRace(RegistrationByOrderDTO registrationByOrderDTO){

        return applicationRepository.findAll().stream()
                .filter(application -> application.getApplicationCode().equals(registrationByOrderDTO.getApplicationCode()))
                .findAny()
                .map(Application::getApplicationRace);

    }

    private Optional<Athlete> getAthlete(RegistrationByOrderDTO registrationByOrderDTO){

        return applicationRepository.findAll().stream()
                .filter(application -> application.getApplicationCode().equals(registrationByOrderDTO.getApplicationCode()))
                .findAny()
                .map(Application::getApplicationAthlete);

    }



    private Optional<Race> getRace(RegistrationByDrawDTO registrationByDrawDTO){

        List<Application> applicationList = applicationRepository.findAll();
        Race race = new Race();

        for(Application application : applicationList)
            if(application.getApplicationRace().getId().equals(registrationByDrawDTO.getIdRace()))
                race = application.getApplicationRace();

        return Optional.of(race);

    }

    private Optional<Athlete> getAthlete(RegistrationByDrawDTO registrationByDrawDTO){

        return applicationRepository.findAll().stream()
                .filter(application -> application.getApplicationAthlete().getId().equals(registrationByDrawDTO.getIdAthlete()))
                .findAny()
                .map(Application::getApplicationAthlete);

    }


}

