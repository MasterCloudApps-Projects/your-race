package es.codeurjc.mastercloudapps.your_race.domain;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import javax.persistence.*;

import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Race {

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

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column
    private LocalDateTime date;

    @Column(nullable = false)
    private String location;

    @Column
    private Double distance;

    @Column
    private String type;

    @Column
    private Integer athleteCapacity;

    @OneToMany(mappedBy = "race")
    private Set<Track> raceTracks;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "application_period_id")
    private ApplicationPeriod applicationPeriod;

    @OneToMany(mappedBy = "applicationRace")
    private Set<Application> applicationRaceApplications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "race_registration_id")
    private Registration raceRegistration;


    public boolean isValid() {

        return  Optional.ofNullable(this.name).isPresent()
                && Optional.ofNullable(this.location).isPresent()
             &&  (Optional.ofNullable(this.raceRegistration).isEmpty() ||
                this.raceRegistration.getRegistrationType().equals(RegistrationType.BYORDER)
                || this.raceRegistration.getRegistrationType().equals(RegistrationType.BYDRAWING));
    }
}
