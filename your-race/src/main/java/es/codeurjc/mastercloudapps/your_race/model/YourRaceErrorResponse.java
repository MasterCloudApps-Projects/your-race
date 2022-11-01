package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YourRaceErrorResponse {
    String exception;
    String message;

}
