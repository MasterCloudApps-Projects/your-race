package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.domain.Application;
import es.codeurjc.mastercloudapps.your_race.domain.Race;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationDTO;
import es.codeurjc.mastercloudapps.your_race.model.AthleteDTO;
import es.codeurjc.mastercloudapps.your_race.model.RaceDTO;
import es.codeurjc.mastercloudapps.your_race.model.TrackDTO;
import es.codeurjc.mastercloudapps.your_race.service.AthleteService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/athletes", produces = MediaType.APPLICATION_JSON_VALUE)
public class AthleteResource {

    private final AthleteService athleteService;

    public AthleteResource(final AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    @GetMapping
    public ResponseEntity<List<AthleteDTO>> getAllAthletes() {
        return ResponseEntity.ok(athleteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AthleteDTO> getAthlete(@PathVariable final Long id) {
        return ResponseEntity.ok(athleteService.get(id));
    }


    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAthlete(@RequestBody @Valid final AthleteDTO athleteDTO) {
        return new ResponseEntity<>(athleteService.create(athleteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAthlete(@PathVariable final Long id,
            @RequestBody @Valid final AthleteDTO athleteDTO) {
        athleteService.update(id, athleteDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAthlete(@PathVariable final Long id) {
        athleteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
