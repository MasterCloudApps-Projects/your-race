package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Organizer;
import es.codeurjc.mastercloudapps.your_race.model.OrganizerDTO;
import es.codeurjc.mastercloudapps.your_race.repos.OrganizerRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class OrganizerService {

    private final OrganizerRepository organizerRepository;

    public OrganizerService(final OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    public List<OrganizerDTO> findAll() {
        return organizerRepository.findAll(Sort.by("id"))
                .stream()
                .map(organizer -> mapToDTO(organizer, new OrganizerDTO()))
                .collect(Collectors.toList());
    }

    public OrganizerDTO get(final Long id) {
        return organizerRepository.findById(id)
                .map(organizer -> mapToDTO(organizer, new OrganizerDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final OrganizerDTO organizerDTO) {
        final Organizer organizer = new Organizer();
        mapToEntity(organizerDTO, organizer);
        return organizerRepository.save(organizer).getId();
    }

    public void update(final Long id, final OrganizerDTO organizerDTO) {
        final Organizer organizer = organizerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(organizerDTO, organizer);
        organizerRepository.save(organizer);
    }

    public void delete(final Long id) {
        organizerRepository.deleteById(id);
    }

    private OrganizerDTO mapToDTO(final Organizer organizer, final OrganizerDTO organizerDTO) {
        organizerDTO.setId(organizer.getId());
        organizerDTO.setName(organizer.getName());
        organizerDTO.setSuscription(organizer.getSuscription());
        organizerDTO.setSuscriptionStatus(organizer.getSuscriptionStatus());
        return organizerDTO;
    }

    private Organizer mapToEntity(final OrganizerDTO organizerDTO, final Organizer organizer) {
        organizer.setName(organizerDTO.getName());
        organizer.setSuscription(organizerDTO.getSuscription());
        organizer.setSuscriptionStatus(organizerDTO.getSuscriptionStatus());
        return organizer;
    }

}
