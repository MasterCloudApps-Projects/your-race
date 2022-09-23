package es.codeurjc.mastercloudapps.your_race.model;

import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackDTO {

    private Long id;

    private LocalDateTime registrationDate;

    @Size(max = 255)
    private String status;

    @Valid
    private Score score;

    private Integer dorsal;

    @Size(max = 255)
    private String paymentInfo;

    private Long race;

    private Long athlete;

}
