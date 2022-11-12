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

    private final FeatureManager featureManager;
    

    public AthleteService(final AthleteRepository athleteRepository,AthleteMongoRepository athleteMongoRepository,
                          FeatureManager featureManager) {
        this.athleteRepository = athleteRepository;
        this.athleteMongoRepository = athleteMongoRepository;
        this.featureManager = featureManager;
    }

    public List<AthleteDTO> findAll() {

        if (this.featureManager.isActive(Features.USEMONGO))
            return athleteMongoRepository.findAll(Sort.by("id"))
                    .stream()
                    .map(athlete -> mapMongoToDTO(athlete, new AthleteDTO()))
                    .toList();

        else
            return athleteRepository.findAll(Sort.by("id"))
                .stream()
                .map(athlete -> mapToDTO(athlete, new AthleteDTO()))
                .toList();
    }

    public AthleteDTO get(final String id) {

        if (this.featureManager.isActive(Features.USEMONGO)) {
            return athleteMongoRepository.findById(id)
                    .map(athlete -> mapMongoToDTO(athlete, new AthleteDTO()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
        else
            return athleteRepository.findById(Long.valueOf(id))
                    .map(athlete -> mapToDTO(athlete, new AthleteDTO()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Optional<Long> create(final AthleteDTO athleteDTO) {

          if (this.featureManager.isActive(Features.USEMONGO))   {
            es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete mongoAthlete =
                    new  es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete();
             athleteMongoRepository.save(mapToMongoEntity(athleteDTO,mongoAthlete));
            return Optional.empty();
        }
        else {
            final Athlete athlete = new Athlete();
            mapToEntity(athleteDTO, athlete);
            return Optional.of(athleteRepository.save(athlete).getId());
        }
    }

    public void update(final String id, final AthleteDTO athleteDTO) {

        if (this.featureManager.isActive(Features.USEMONGO)){
            final es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete athlete = athleteMongoRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            mapToMongoEntity(athleteDTO, athlete);
            athleteMongoRepository.save(athlete);

        }
        else
        {
            final Athlete athlete = athleteRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            mapToEntity(athleteDTO, athlete);
            athleteRepository.save(athlete);
        }
    }

    public void delete(final String id) {
        if (this.featureManager.isActive(Features.USEMONGO))
          athleteMongoRepository.deleteById(id);
        else
         athleteRepository.deleteById(Long.valueOf(id));
    }


    private AthleteDTO mapToDTO(final Athlete athlete, final AthleteDTO athleteDTO) {
        athleteDTO.setId(athlete.getId().toString());
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


    private es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete mapToMongoEntity(final AthleteDTO athleteDTO,
                                        final es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete athlete) {
        athlete.setName(athleteDTO.getName());
        athlete.setSurname(athleteDTO.getSurname());
        athlete.setTrackRecord(athleteDTO.getTrackRecord());
        return athlete;
    }

    private AthleteDTO mapMongoToDTO(final es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete athlete, final AthleteDTO athleteDTO) {
        athleteDTO.setId(athlete.getId());
        athleteDTO.setName(athlete.getName());
        athleteDTO.setSurname(athlete.getSurname());
        athleteDTO.setTrackRecord(athlete.getTrackRecord());
        return athleteDTO;
    }

}
