package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.domain.Registration;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationDTO;
import es.codeurjc.mastercloudapps.your_race.repos.AthleteRepository;
import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
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
    private final RaceRepository raceRepository;
    private final AthleteRepository athleteRepository;

    public RegistrationService(final RegistrationRepository registrationRepository,
            final RaceRepository raceRepository, final AthleteRepository athleteRepository) {
        this.registrationRepository = registrationRepository;
        this.raceRepository = raceRepository;
        this.athleteRepository = athleteRepository;
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
        registrationDTO.setDate(registration.getDate());
        registrationDTO.setStatus(registration.getStatus());
        registrationDTO.setScore(registration.getScore());
        registrationDTO.setDorsal(registration.getDorsal());
        registrationDTO.setType(registration.getType());
        registrationDTO.setPaymentInfo(registration.getPaymentInfo());
        registrationDTO.setRegistration(registration.getRegistration() == null ? null : registration.getRegistration().getId());
        registrationDTO.setAthleteRegistration(registration.getAthleteRegistration() == null ? null : registration.getAthleteRegistration().getId());
        return registrationDTO;
    }

    private Registration mapToEntity(final RegistrationDTO registrationDTO,
            final Registration registration) {
        registration.setDate(registrationDTO.getDate());
        registration.setStatus(registrationDTO.getStatus());
        registration.setScore(registrationDTO.getScore());
        registration.setDorsal(registrationDTO.getDorsal());
        registration.setType(registrationDTO.getType());
        registration.setPaymentInfo(registrationDTO.getPaymentInfo());
        final Race registration = registrationDTO.getRegistration() == null ? null : raceRepository.findById(registrationDTO.getRegistration())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "registration not found"));
        registration.setRegistration(registration);
        final Athlete athleteRegistration = registrationDTO.getAthleteRegistration() == null ? null : athleteRepository.findById(registrationDTO.getAthleteRegistration())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "athleteRegistration not found"));
        registration.setAthleteRegistration(athleteRegistration);
        return registration;
    }

}
