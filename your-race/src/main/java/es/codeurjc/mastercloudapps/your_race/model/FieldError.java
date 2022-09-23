package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FieldError {

    private String field;
    private String errorCode;

}
