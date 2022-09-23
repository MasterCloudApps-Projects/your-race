package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Registration;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationDTO;
import es.codeurjc.mastercloudapps.your_race.repos.RegistrationRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public RegistrationService(final RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public List<RegistrationDTO> findAll() {
        return registrationRepository.findAll(Sort.by("id"))
                .stream()
                .map(registration -> mapToDTO(registration, new RegistrationDTO()))
                .collect(Collectors.toList());
    }

    public RegistrationDTO get(final Long id) {
        return registrationRepository.findById(id)
                .map(registration -> mapToDTO(registration, new RegistrationDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final RegistrationDTO registrationDTO) {
        final Registration registration = new Registration();
        mapToEntity(registrationDTO, registration);
        return registrationRepository.save(registration).getId();
    }

    public void update(final Long id, final RegistrationDTO registrationDTO) {
        final Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(registrationDTO, registration);
        registrationRepository.save(registration);
    }

    public void delete(final Long id) {
        registrationRepository.deleteById(id);
    }

    private RegistrationDTO mapToDTO(final Registration registration,
            final RegistrationDTO registrationDTO) {
        registrationDTO.setId(registration.getId());
        registrationDTO.setRegistrationType(registration.getRegistrationType());
        registrationDTO.setRegistrationDate(registration.getRegistrationDate());
        registrationDTO.setRegistrationCost(registration.getRegistrationCost());
        registrationDTO.setConcurrentRequestThreshold(registration.getConcurrentRequestThreshold());
        return registrationDTO;
    }

    private Registration mapToEntity(final RegistrationDTO registrationDTO,
            final Registration registration) {
        registration.setRegistrationType(registrationDTO.getRegistrationType());
        registration.setRegistrationDate(registrationDTO.getRegistrationDate());
        registration.setRegistrationCost(registrationDTO.getRegistrationCost());
        registration.setConcurrentRequestThreshold(registrationDTO.getConcurrentRequestThreshold());
        return registration;
    }

}
