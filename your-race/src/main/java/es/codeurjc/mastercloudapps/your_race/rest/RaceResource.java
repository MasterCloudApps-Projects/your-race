package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.model.RaceDTO;
import es.codeurjc.mastercloudapps.your_race.service.RaceService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/races", produces = MediaType.APPLICATION_JSON_VALUE)
public class RaceResource {

    private final RaceService raceService;

    public RaceResource(final RaceService raceService) {
        this.raceService = raceService;

    }

    @GetMapping
    public ResponseEntity<List<RaceDTO>> getAllRaces(@RequestParam boolean open) {

        if (open)
            return ResponseEntity.ok(raceService.findOpenRaces());
        else
            return ResponseEntity.ok(raceService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RaceDTO> getRace(@PathVariable final Long id) {
        return ResponseEntity.ok(raceService.get(id));
    }


    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRace(@RequestBody @Valid final RaceDTO raceDTO) {
        return new ResponseEntity<>(raceService.create(raceDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRace(@PathVariable final Long id,
            @RequestBody @Valid final RaceDTO raceDTO) {
        raceService.update(id, raceDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRace(@PathVariable final Long id) {
        raceService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
