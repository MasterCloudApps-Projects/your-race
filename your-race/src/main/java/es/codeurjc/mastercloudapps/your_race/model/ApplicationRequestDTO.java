package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRequestDTO {

   private Long athleteId;
   private Long raceId;
}

