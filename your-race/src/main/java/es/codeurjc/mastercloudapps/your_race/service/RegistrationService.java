package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.RegistrationInfo;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationDTO;
import es.codeurjc.mastercloudapps.your_race.repos.RegistrationRepository;
import java.util.List;

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
                .toList();
    }

    public RegistrationDTO get(final Long id) {
        return registrationRepository.findById(id)
                .map(registration -> mapToDTO(registration, new RegistrationDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final RegistrationDTO registrationDTO) {
        final RegistrationInfo registrationInfo = new RegistrationInfo();
        mapToEntity(registrationDTO, registrationInfo);
        return registrationRepository.save(registrationInfo).getId();
    }

    public void update(final Long id, final RegistrationDTO registrationDTO) {
        final RegistrationInfo registrationInfo = registrationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(registrationDTO, registrationInfo);
        registrationRepository.save(registrationInfo);
    }

    public void delete(final Long id) {
        registrationRepository.deleteById(id);
    }

    private RegistrationDTO mapToDTO(final RegistrationInfo registrationInfo,
            final RegistrationDTO registrationDTO) {
        registrationDTO.setId(registrationInfo.getId());
        registrationDTO.setRegistrationType(registrationInfo.getRegistrationType());
        registrationDTO.setRegistrationDate(registrationInfo.getRegistrationDate());
        registrationDTO.setRegistrationCost(registrationInfo.getRegistrationCost());
        registrationDTO.setConcurrentRequestThreshold(registrationInfo.getConcurrentRequestThreshold());
        return registrationDTO;
    }

    private RegistrationInfo mapToEntity(final RegistrationDTO registrationDTO,
                                         final RegistrationInfo registrationInfo) {
        registrationInfo.setRegistrationType(registrationDTO.getRegistrationType());
        registrationInfo.setRegistrationDate(registrationDTO.getRegistrationDate());
        registrationInfo.setRegistrationCost(registrationDTO.getRegistrationCost());
        registrationInfo.setConcurrentRequestThreshold(registrationDTO.getConcurrentRequestThreshold());
        return registrationInfo;
    }

}
