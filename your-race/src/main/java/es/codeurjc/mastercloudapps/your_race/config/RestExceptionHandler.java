package es.codeurjc.mastercloudapps.your_race.config;

import es.codeurjc.mastercloudapps.your_race.domain.exception.YourRaceException;
import es.codeurjc.mastercloudapps.your_race.model.ErrorResponse;
import es.codeurjc.mastercloudapps.your_race.model.FieldError;
import es.codeurjc.mastercloudapps.your_race.model.YourRaceErrorResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

  @ExceptionHandler(YourRaceException.class)
    public ResponseEntity<YourRaceErrorResponse> handleThrowable(final Throwable exception) {

        final YourRaceErrorResponse errorResponse = YourRaceErrorResponse.builder()
                        .exception(exception.getClass().getSimpleName().toString())
                        .message(exception.getMessage())
                        .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
