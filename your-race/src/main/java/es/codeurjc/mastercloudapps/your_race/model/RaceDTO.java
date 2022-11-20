package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


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
    private RaceStatus raceStatus;
    private Integer availableCapacity;

    private LocalDateTime applicationInitialDate;
    private LocalDateTime applicationLastDate;


    private String organizerName;

    private LocalDateTime raceRegistrationDate;
    private RegistrationType registrationType;
    private Double registrationCost;







}
