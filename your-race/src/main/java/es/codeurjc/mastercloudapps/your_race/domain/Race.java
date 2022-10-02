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


    private boolean nameIsPresent(){
        return Optional.ofNullable(this.name).isPresent();
    }
    private boolean locationIsPresent(){
        return Optional.ofNullable(this.location).isPresent();
    }
    private boolean raceRegistrationIsValid(){
        if (Optional.ofNullable(this.raceRegistration).isEmpty())
            return true;
        if (Optional.ofNullable(this.raceRegistration.getRegistrationType()).isEmpty())
            return true;
        if (this.raceRegistration.getRegistrationType().equals(RegistrationType.BYORDER))
            return true;
        return this.raceRegistration.getRegistrationType().equals(RegistrationType.BYDRAWING);


    }
    private boolean athleteCapacityIsValid(){
        return Optional.ofNullable(this.athleteCapacity).isEmpty()
                || athleteCapacity.compareTo(0) > 0;
    }
    private boolean distanceIsValid(){
        if (Optional.ofNullable(this.distance).isEmpty())
                return true;
        if (this.distance > 0)
            return true;
        return false;



    }
    private boolean concurrentThresholdIsValid()
    {
        if (Optional.ofNullable(this.raceRegistration).isEmpty())
            return true;
        if (Optional.ofNullable(this.raceRegistration.getConcurrentRequestThreshold()).isEmpty())
            return true;
        if (this.raceRegistration.getConcurrentRequestThreshold()>1)
            return true;
        return false;
    }

    private boolean registrationDateIsValid(){
        if (Optional.ofNullable(this.raceRegistration).isEmpty())
                return true;
        if (Optional.ofNullable(this.raceRegistration.getRegistrationDate()).isEmpty())
            return true;
        if (this.raceRegistration.getRegistrationDate().isAfter(LocalDateTime.now()))
            return true;
        return false;
    }

    private boolean applicationPeriodIsValid(){
        if (Optional.ofNullable(this.applicationPeriod).isEmpty())
            return true;
        if (Optional.ofNullable(this.applicationPeriod.getInitialDate()).isEmpty()
        && Optional.ofNullable(this.applicationPeriod.getLastDate()).isEmpty())
            return true;
        if (Optional.ofNullable(this.applicationPeriod.getInitialDate()).isPresent()
                && Optional.ofNullable(this.applicationPeriod.getLastDate()).isPresent()
           && this.applicationPeriod.getInitialDate().isBefore(this.applicationPeriod.getLastDate()))
            return true;
        return false;
    }
    private boolean datesAreValid(){
        if (Optional.ofNullable(this.raceRegistration).isEmpty()
                && Optional.ofNullable(this.applicationPeriod).isEmpty())
            return true;
        if (Optional.ofNullable(this.raceRegistration.getRegistrationDate()).isEmpty()
        && Optional.ofNullable(this.applicationPeriod.getInitialDate()).isEmpty()
                && Optional.ofNullable(this.applicationPeriod.getLastDate()).isEmpty())
            return true;
        if (Optional.ofNullable(this.raceRegistration.getRegistrationDate()).isPresent()  && Optional.ofNullable(this.applicationPeriod.getInitialDate()).isEmpty()
                && Optional.ofNullable(this.applicationPeriod.getLastDate()).isEmpty())
            return true;

        return false;
    }
    public boolean isValid() {
        return  nameIsPresent()
                && locationIsPresent()
                && raceRegistrationIsValid()
                && athleteCapacityIsValid()
                && distanceIsValid()
                && concurrentThresholdIsValid()
                && registrationDateIsValid()
                && applicationPeriodIsValid()
                && datesAreValid();
    }
}
