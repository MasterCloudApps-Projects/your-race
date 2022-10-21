package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDTO {

   private String applicationCode;
   private String name;
   private String surname;

   private String raceName;

  // private LocalDateTime date;
  // private LocalDateTime raceRegistrationDate;
}

