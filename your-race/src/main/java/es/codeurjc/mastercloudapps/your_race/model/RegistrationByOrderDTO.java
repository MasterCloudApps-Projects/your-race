package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationByOrderDTO {
    @NotNull
    private Long idAthlete;
    @NotNull
    @Size(max = 50)
    private String applicationCode;
}
