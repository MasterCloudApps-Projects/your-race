package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.domain.Application;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationDTO;
import es.codeurjc.mastercloudapps.your_race.model.AthleteDTO;
import es.codeurjc.mastercloudapps.your_race.model.RaceDTO;
import es.codeurjc.mastercloudapps.your_race.service.AthleteService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.Optional;
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


    @GetMapping("/{id}/races")
    public ResponseEntity<List<RaceDTO>> getAthleteRaces(@PathVariable final Long id){
        return ResponseEntity.ok(null);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAthlete(@RequestBody @Valid final AthleteDTO athleteDTO) {
        return new ResponseEntity<>(athleteService.create(athleteDTO), HttpStatus.CREATED);
    }

    //Cambiar a ApplicationDTO
    @PostMapping("/{id}/application/{idRace}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Optional<ApplicationDTO>> createApplication(@PathVariable final Long id, @PathVariable final Long idRace){
        Optional<ApplicationDTO> applicationDTO = athleteService.raceApplication(id,idRace);
        if (applicationDTO.isPresent())
         //return ResponseEntity.ok(application);
            return new ResponseEntity<>(applicationDTO, HttpStatus.CREATED);
        else
          return ResponseEntity.ok(Optional.empty()); //Mirar qué valor debería devolver aquí.


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
