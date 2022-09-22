package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.model.TrackDTO;
import es.codeurjc.mastercloudapps.your_race.service.TrackService;
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
@RequestMapping(value = "/api/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrackResource {

    private final TrackService trackService;

    public TrackResource(final TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public ResponseEntity<List<TrackDTO>> getAllTracks() {
        return ResponseEntity.ok(trackService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackDTO> getTrack(@PathVariable final Long id) {
        return ResponseEntity.ok(trackService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTrack(@RequestBody @Valid final TrackDTO trackDTO) {
        return new ResponseEntity<>(trackService.create(trackDTO), HttpStatus.CREATED);
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