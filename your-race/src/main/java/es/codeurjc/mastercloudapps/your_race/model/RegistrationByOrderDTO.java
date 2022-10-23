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
public class RegistrationByOrderDTO extends RegistrationDTO {

    @NotNull
    @Size(max = 50)
    private String applicationCode;
}
