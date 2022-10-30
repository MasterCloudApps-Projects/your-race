package es.codeurjc.mastercloudapps.your_race.model;

import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackDTO {

    private Long id;

    @NotNull
    private Long athleteId;
    private String name;
    private String surname;

    @NotNull
    private Long raceId;
    private String raceName;
  //  private LocalDateTime raceDate;

  //  private LocalDateTime registrationDate;
    @Size(max = 255)
    private String paymentInfo;

    @Size(max = 255)
    private String status;

    @Valid
    private Score score;

    private Integer dorsal;



}


