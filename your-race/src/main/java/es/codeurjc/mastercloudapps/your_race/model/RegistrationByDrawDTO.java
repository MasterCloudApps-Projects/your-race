package es.codeurjc.mastercloudapps.your_race.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class RegistrationByDrawDTO {

    @NotNull
    private Long athleteId;
    @NotNull
    private Long raceId;


}
