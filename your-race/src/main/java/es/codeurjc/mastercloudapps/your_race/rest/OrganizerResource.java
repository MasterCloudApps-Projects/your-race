package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.model.OrganizerDTO;
import es.codeurjc.mastercloudapps.your_race.service.OrganizerService;
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
@RequestMapping(value = "/api/organizers", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizerResource {

    private final OrganizerService organizerService;

    public OrganizerResource(final OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @GetMapping
    public ResponseEntity<List<OrganizerDTO>> getAllOrganizers() {
        return ResponseEntity.ok(organizerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizerDTO> getOrganizer(@PathVariable final Long id) {
        return ResponseEntity.ok(organizerService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOrganizer(
            @RequestBody @Valid final OrganizerDTO organizerDTO) {
        return new ResponseEntity<>(organizerService.create(organizerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrganizer(@PathVariable final Long id,
            @RequestBody @Valid final OrganizerDTO organizerDTO) {
        organizerService.update(id, organizerDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOrganizer(@PathVariable final Long id) {
        organizerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
