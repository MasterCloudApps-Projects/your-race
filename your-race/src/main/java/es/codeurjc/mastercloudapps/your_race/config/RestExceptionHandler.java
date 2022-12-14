package es.codeurjc.mastercloudapps.your_race.config;

import es.codeurjc.mastercloudapps.your_race.domain.exception.YourRaceException;
import es.codeurjc.mastercloudapps.your_race.domain.exception.notfound.YourRaceNotFoundException;
import es.codeurjc.mastercloudapps.your_race.model.YourRaceErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

  @ExceptionHandler(YourRaceException.class)
    public ResponseEntity<YourRaceErrorResponse> handleThrowable(final Throwable exception) {

        final YourRaceErrorResponse errorResponse = YourRaceErrorResponse.builder()
                        .exception(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(YourRaceNotFoundException.class)
    public ResponseEntity<YourRaceErrorResponse> handleThrowableNotFound(final Throwable exception) {

        final YourRaceErrorResponse errorResponse = YourRaceErrorResponse.builder()
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
