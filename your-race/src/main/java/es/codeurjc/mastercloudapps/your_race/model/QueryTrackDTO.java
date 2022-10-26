package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryTrackDTO {


    private Long athleteId;
    private Long raceId;



}


