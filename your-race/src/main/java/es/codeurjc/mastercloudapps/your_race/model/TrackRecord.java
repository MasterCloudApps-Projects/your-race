package es.codeurjc.mastercloudapps.your_race.model;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TrackRecord {

    @Size(max = 255)
    private String raceName;

    @Valid
    private Score score;

    private LocalDate date;

}
