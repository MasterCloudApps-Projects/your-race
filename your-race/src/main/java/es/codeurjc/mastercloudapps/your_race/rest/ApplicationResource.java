package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.model.ApplicationDTO;
import es.codeurjc.mastercloudapps.your_race.model.ApplicationRequestDTO;
import es.codeurjc.mastercloudapps.your_race.service.ApplicationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value ="/api/applications", produces= MediaType.APPLICATION_JSON_VALUE)
public class ApplicationResource {

    private final ApplicationService applicationService;

    public ApplicationResource(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @GetMapping("/athletes/{id}")
    public ResponseEntity<List<ApplicationDTO>> getAthleteApplicationRaces(@PathVariable final Long id, @RequestParam boolean open){

        if (open)
            return ResponseEntity.ok(applicationService.findAllApplicationOpenRace(id));
        return ResponseEntity.ok(applicationService.findAllApplication(id));
    }

    @PostMapping()
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Optional<ApplicationDTO>> createApplication(@RequestBody final ApplicationRequestDTO applicationRequestDTO){
        try {

            Optional<ApplicationDTO> applicationDTO = applicationService.raceApplication(applicationRequestDTO);
            if (applicationDTO.isPresent())
                return new ResponseEntity<>(applicationDTO, HttpStatus.CREATED);
            else
                return  new ResponseEntity<>( HttpStatus.NOT_FOUND);

        } catch (Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

        }
    }

   /* @PostMapping("/{id}/applications/{idRace}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Optional<ApplicationDTO>> createApplication(@PathVariable final Long id, @PathVariable final Long idRace){
        try {
            Optional<ApplicationDTO> applicationDTO = applicationService.raceApplication(id, idRace);
            if (applicationDTO.isPresent())
                return new ResponseEntity<>(applicationDTO, HttpStatus.CREATED);
            else
                return  new ResponseEntity<>( HttpStatus.NOT_FOUND);

        } catch (Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

        }
    }*/
}
