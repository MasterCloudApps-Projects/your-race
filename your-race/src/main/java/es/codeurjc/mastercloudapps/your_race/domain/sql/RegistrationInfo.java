package es.codeurjc.mastercloudapps.your_race.domain.sql;

import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegistrationInfo {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private RegistrationType registrationType;

    @Column
    private LocalDateTime registrationDate;

    @Column
    private Double registrationCost;

    @Column
    private Integer concurrentRequestThreshold;

}
