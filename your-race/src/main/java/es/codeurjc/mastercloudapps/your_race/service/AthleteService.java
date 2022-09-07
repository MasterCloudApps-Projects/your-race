package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.model.AthleteDTO;
import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AthleteService {

    private final AthleteRepository athleteRepository;

    public AthleteService(final AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    public List<AthleteDTO> findAll() {
        return athleteRepository.findAll(Sort.by("id"))
                .stream()
                .map(athlete -> mapToDTO(athlete, new AthleteDTO()))
                .collect(Collectors.toList());
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

}
