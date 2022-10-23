package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class RegistrationByDrawDTO extends RegistrationDTO {

    @NotNull
    private Long idAthlete;
    @NotNull
    private Long idRace;


}
