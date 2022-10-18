package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Track;
import es.codeurjc.mastercloudapps.your_race.model.Score;
import es.codeurjc.mastercloudapps.your_race.model.TrackDTO;
import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import es.codeurjc.mastercloudapps.your_race.repos.TrackRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Size;


@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final RaceRepository raceRepository;
    private final AthleteRepository athleteRepository;


    public TrackService(final TrackRepository trackRepository, final RaceRepository raceRepository,
            final AthleteRepository athleteRepository) {
        this.trackRepository = trackRepository;
        this.raceRepository = raceRepository;
        this.athleteRepository = athleteRepository;
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

    public Long create(final TrackDTO trackDTO) {
        final Track track = new Track();
        mapToEntity(trackDTO, track);
        return trackRepository.save(track).getId();
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
        trackDTO.setRaceDate(track.getRace() == null ? null : track.getRace().getDate());

        trackDTO.setRegistrationDate(track.getRegistrationDate());

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



}

