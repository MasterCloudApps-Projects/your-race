package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ErrorResponse {

    private Integer httpStatus;
    private String exception;
    private String message;
    private List<FieldError> fieldErrors;

}
