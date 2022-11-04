package es.codeurjc.mastercloudapps.your_race.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Application {

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

    @Column(unique=true)
    private String applicationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_race_id")
    @ToString.Exclude
    private Race applicationRace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_athlete_id")
    @ToString.Exclude
    private Athlete applicationAthlete;

}
