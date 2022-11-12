package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AthleteDTO {

    private String id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surname;

    @Valid
    private TrackRecord trackRecord;

}
