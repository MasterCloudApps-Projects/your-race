package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.model.RegistrationDTO;
import es.codeurjc.mastercloudapps.your_race.service.RegistrationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/registrations", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationResource {

    private final RegistrationService registrationService;

    public RegistrationResource(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<List<RegistrationDTO>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> getRegistration(@PathVariable final Long id) {
        return ResponseEntity.ok(registrationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRegistration(
            @RequestBody @Valid final RegistrationDTO registrationDTO) {
        return new ResponseEntity<>(registrationService.create(registrationDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRegistration(@PathVariable final Long id,
            @RequestBody @Valid final RegistrationDTO registrationDTO) {
        registrationService.update(id, registrationDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRegistration(@PathVariable final Long id) {
        registrationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
