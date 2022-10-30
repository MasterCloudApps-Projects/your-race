package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import es.codeurjc.mastercloudapps.your_race.domain.exception.ApplicationCodeNotValidException;
import es.codeurjc.mastercloudapps.your_race.model.*;
import es.codeurjc.mastercloudapps.your_race.repos.ApplicationRepository;
import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import es.codeurjc.mastercloudapps.your_race.repos.TrackRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;




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


    public TrackDTO create(final RegistrationDTO registrationDTO) throws Exception {

       TrackDTO trackDTO = toTrackDTO(registrationDTO,TrackDTO.builder().build());
        if (trackDTO.getAthleteId()==null)
            throw new ApplicationCodeNotValidException("Application code is invalid. Athlete or application race were not found.");

        Track track = trackRepository.save(mapToEntity(trackDTO, new Track()));
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
        trackDTO.setName(track.getAthlete() == null ? null : track.getAthlete().getSurname());

        trackDTO.setRaceId(track.getRace() == null ? null : track.getRace().getId());
        trackDTO.setRaceName(track.getRace() == null ? null : track.getRace().getName());
       // trackDTO.setRaceDate(track.getRace() == null ? null : track.getRace().getDate());

      //  trackDTO.setRegistrationDate(track.getRegistrationDate());

        trackDTO.setStatus(track.getStatus());
        trackDTO.setScore(track.getScore());
        trackDTO.setDorsal(track.getDorsal());
        trackDTO.setPaymentInfo(track.getPaymentInfo());

        return trackDTO;
    }

    private Track mapToEntity(final TrackDTO trackDTO, final Track track) {
      //  track.setRegistrationDate(trackDTO.getRegistrationDate());
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

    private TrackDTO toTrackDTO(final RegistrationDTO registrationDTO, final TrackDTO trackDTO)
    {
        if (registrationDTO.getRegistrationType() != null
                && registrationDTO.getRegistrationType().equals(RegistrationType.BYDRAW))
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

