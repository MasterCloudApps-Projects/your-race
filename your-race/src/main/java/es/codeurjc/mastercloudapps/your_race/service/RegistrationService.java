package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.RegistrationInfo;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationDTO;
import es.codeurjc.mastercloudapps.your_race.repos.RegistrationInfoRepository;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class RegistrationService {

    private final RegistrationInfoRepository registrationInfoRepository;

    public RegistrationService(final RegistrationInfoRepository registrationInfoRepository) {
        this.registrationInfoRepository = registrationInfoRepository;
    }

    public List<RegistrationDTO> findAll() {
        return registrationInfoRepository.findAll(Sort.by("id"))
                .stream()
                .map(registration -> mapToDTO(registration, new RegistrationDTO()))
                .toList();
    }

    public RegistrationDTO get(final Long id) {
        return registrationInfoRepository.findById(id)
                .map(registration -> mapToDTO(registration, new RegistrationDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final RegistrationDTO registrationDTO) {
        final RegistrationInfo registrationInfo = new RegistrationInfo();
        mapToEntity(registrationDTO, registrationInfo);
        return registrationInfoRepository.save(registrationInfo).getId();
    }

    public void update(final Long id, final RegistrationDTO registrationDTO) {
        final RegistrationInfo registrationInfo = registrationInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(registrationDTO, registrationInfo);
        registrationInfoRepository.save(registrationInfo);
    }

    public void delete(final Long id) {
        registrationInfoRepository.deleteById(id);
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
