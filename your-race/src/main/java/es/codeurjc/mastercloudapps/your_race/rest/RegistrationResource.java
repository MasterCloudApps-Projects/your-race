package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.model.RegistrationByDrawDTO;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationByOrderDTO;
import es.codeurjc.mastercloudapps.your_race.model.TrackDTO;
import es.codeurjc.mastercloudapps.your_race.service.RegistrationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationResource {

    private final RegistrationService registrationService;

    public RegistrationResource(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<List<TrackDTO>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.findAll());
    }

    @PostMapping(value = "/api/registrations")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRegistrationByOrder(
            @RequestBody @Valid final RegistrationByOrderDTO registrationByOrderDTO) {
        return new ResponseEntity<>(registrationService.create(registrationByOrderDTO), HttpStatus.CREATED);
    }

    @PostMapping (value = "/api/draws")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRegistrationByDraw(@RequestBody @Valid final RegistrationByDrawDTO registrationByDrawDTO) {
            return new ResponseEntity<>(registrationService.createByDraw(registrationByDrawDTO), HttpStatus.CREATED);
    }

}
