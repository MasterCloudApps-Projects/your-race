package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.Features;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Athlete;
import es.codeurjc.mastercloudapps.your_race.model.AthleteDTO;
import es.codeurjc.mastercloudapps.your_race.repos.mongo.AthleteMongoRepository;
import es.codeurjc.mastercloudapps.your_race.repos.sql.AthleteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.togglz.core.manager.FeatureManager;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private final AthleteMongoRepository athleteMongoRepository;

    private FeatureManager featureManager;
    

    public AthleteService(final AthleteRepository athleteRepository,AthleteMongoRepository athleteMongoRepository,
                          FeatureManager featureManager) {
        this.athleteRepository = athleteRepository;
        this.athleteMongoRepository = athleteMongoRepository;
        this.featureManager = featureManager;
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

    public Optional<Long> create(final AthleteDTO athleteDTO) {

        if (this.featureManager!=null && this.featureManager.isActive(Features.USEMONGO))       {
            es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete mongoAthlete =
                    new  es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete();
             athleteMongoRepository.save(mapToMongo(athleteDTO,mongoAthlete));
            return Optional.empty();
        }
        else {
            final Athlete athlete = new Athlete();
            mapToEntity(athleteDTO, athlete);
            return Optional.of(athleteRepository.save(athlete).getId());
        }
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


    private es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete mapToMongo(final AthleteDTO athleteDTO,
                                        final es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete athlete) {
        athlete.setName(athleteDTO.getName());
        athlete.setSurname(athleteDTO.getSurname());
        athlete.setTrackRecord(athleteDTO.getTrackRecord());
        return athlete;
    }

}
