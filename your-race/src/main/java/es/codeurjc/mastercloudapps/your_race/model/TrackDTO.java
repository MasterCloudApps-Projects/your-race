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
public class TrackDTO {

    private String id;

    @NotNull
    private String athleteId;
    private String name;
    private String surname;

    @NotNull
    private String raceId;
    private String raceName;

    private LocalDateTime raceDate;

    private LocalDateTime registrationDate;
    @Size(max = 255)
    private String paymentInfo;

    @Size(max = 255)
    private String status;

    @Valid
    private Score score;

    private Integer dorsal;



}


