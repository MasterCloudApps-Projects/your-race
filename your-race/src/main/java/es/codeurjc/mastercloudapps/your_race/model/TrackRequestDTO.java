package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackRequestDTO {


    private Long athleteId;
    private Long raceId;



}


