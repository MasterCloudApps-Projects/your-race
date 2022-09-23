package es.codeurjc.mastercloudapps.your_race.model;

import java.time.LocalDateTime;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDTO {

    private Long id;
    private RegistrationType registrationType;
    private LocalDateTime registrationDate;
    private Double registrationCost;
    private Integer concurrentRequestThreshold;

}
