package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.domain.exception.ApplicationCodeNotValidException;
import es.codeurjc.mastercloudapps.your_race.domain.exception.RaceFullCapacityException;
import es.codeurjc.mastercloudapps.your_race.model.*;
import es.codeurjc.mastercloudapps.your_race.service.TrackService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrackResource {

    private final TrackService trackService;

    public TrackResource(final TrackService trackService) {
        this.trackService = trackService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<TrackDTO> getTrack(@PathVariable final Long id) {
        return ResponseEntity.ok(trackService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<TrackDTO>> getTracks(@RequestParam boolean open, @RequestBody @Valid final TrackRequestDTO trackDTO){

        if (trackDTO.getAthleteId() == null)
            return ResponseEntity.ok(trackService.findAll());

        if (open)
            return ResponseEntity.ok(trackService.findAllOpenByAthlete(trackDTO.getAthleteId()));
        return ResponseEntity.ok(trackService.findAllByAthlete(trackDTO.getAthleteId()));
    }


    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<TrackDTO> createRegistration(
            @RequestBody @Valid final RegistrationDTO registrationDTO)
            throws ApplicationCodeNotValidException, RaceFullCapacityException {

           if(registrationDTO.getClass().equals(RegistrationByOrderDTO.class))
                 registrationDTO.setRegistrationType(RegistrationType.BYORDER);
            else
                registrationDTO.setRegistrationType(RegistrationType.BYDRAW);

            TrackDTO trackDTO = trackService.create(registrationDTO);
            return new ResponseEntity<>(trackDTO, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTrack(@PathVariable final Long id,
            @RequestBody @Valid final TrackDTO trackDTO) {
        trackService.update(id, trackDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTrack(@PathVariable final Long id) {
        trackService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
