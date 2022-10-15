package es.codeurjc.mastercloudapps.your_race.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaceDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 1000)
    private String description;

    private LocalDateTime date;

    @NotNull
    @Size(max = 255)
    private String location;

    private Double distance;

    @Size(max = 255)
    private String type;

    private Integer athleteCapacity;

    //private Long applicationPeriod;
    private LocalDateTime applicationInitialDate;
    private LocalDateTime applicationLastDate;

    //@NotNull
    private String organizerName;

    private Long raceRegistration;

}
