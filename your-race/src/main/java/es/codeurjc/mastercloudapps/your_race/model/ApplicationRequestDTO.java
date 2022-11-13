package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRequestDTO {

   private String athleteId;
   private String raceId;
}

