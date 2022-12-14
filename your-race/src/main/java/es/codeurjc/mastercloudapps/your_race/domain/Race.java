package es.codeurjc.mastercloudapps.your_race.domain;

import es.codeurjc.mastercloudapps.your_race.domain.exception.RaceFullCapacityException;
import es.codeurjc.mastercloudapps.your_race.model.RaceStatus;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Slf4j
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

    @Column
    private RaceStatus raceStatus;

    @ToString.Exclude
    @OneToMany(mappedBy = "race")
    private Set<Track> raceTracks;

    @ToString.Exclude
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "application_period_id")
    private ApplicationPeriod applicationPeriod;

    @ToString.Exclude
    @OneToMany(mappedBy = "applicationRace")
    private Set<Application> applicationRaceApplications;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

    @ToString.Exclude
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "race_registration_info_id")
    private RegistrationInfo raceRegistrationInfo;


    private boolean nameIsPresent(){
        return Optional.ofNullable(this.name).isPresent();
    }
    private boolean locationIsPresent(){
        return Optional.ofNullable(this.location).isPresent();
    }
    private boolean raceRegistrationIsValid(){
        if (Optional.ofNullable(this.raceRegistrationInfo).isEmpty())
            return true;
        if (Optional.ofNullable(this.raceRegistrationInfo.getRegistrationType()).isEmpty())
            return true;
        if (this.raceRegistrationInfo.getRegistrationType().equals(RegistrationType.BYORDER))
            return true;
        return this.raceRegistrationInfo.getRegistrationType().equals(RegistrationType.BYDRAW);
    }
    private boolean athleteCapacityIsValid(){
        return Optional.ofNullable(this.athleteCapacity).isEmpty()
                || athleteCapacity.compareTo(0) > 0;
    }
    private boolean distanceIsValid(){
        if (Optional.ofNullable(this.distance).isEmpty())
                return true;
        return this.distance > 0;


    }
    private boolean concurrentThresholdIsValid()
    {
        if (Optional.ofNullable(this.raceRegistrationInfo).isEmpty())
            return true;
        if (Optional.ofNullable(this.raceRegistrationInfo.getConcurrentRequestThreshold()).isEmpty())
            return true;
        return this.raceRegistrationInfo.getConcurrentRequestThreshold() > 1;
    }

    private boolean registrationDateIsValid(){
        if (Optional.ofNullable(this.raceRegistrationInfo).isEmpty())
                return true;
        if (Optional.ofNullable(this.raceRegistrationInfo.getRegistrationDate()).isEmpty())
            return true;
        return this.raceRegistrationInfo.getRegistrationDate().isAfter(LocalDateTime.now());
    }

    private boolean applicationPeriodIsValid(){
        if (Optional.ofNullable(this.applicationPeriod).isEmpty())
            return true;
        if (Optional.ofNullable(this.applicationPeriod.getInitialDate()).isEmpty()
        && Optional.ofNullable(this.applicationPeriod.getLastDate()).isEmpty())
            return true;
        return Optional.ofNullable(this.applicationPeriod.getInitialDate()).isPresent()
                && Optional.ofNullable(this.applicationPeriod.getLastDate()).isPresent()
                && this.applicationPeriod.getInitialDate().isBefore(this.applicationPeriod.getLastDate());
    }
    private boolean datesAreValid(){

        if (Optional.ofNullable(this.raceRegistrationInfo).isEmpty())
            return true;
        if (Optional.ofNullable(this.applicationPeriod).isEmpty())
            return true;
       if (Optional.ofNullable(this.raceRegistrationInfo.getRegistrationDate()).isEmpty())
            return true;
        if (Optional.ofNullable(this.applicationPeriod.getInitialDate()).isEmpty()
                && Optional.ofNullable(this.applicationPeriod.getLastDate()).isEmpty())
            return true;
        return this.raceRegistrationInfo.getRegistrationDate().isAfter(this.applicationPeriod.getLastDate());
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

    public boolean isOpen(){
      return LocalDateTime.now().isBefore(this.date);
    }

    public int getRaceTracks(){
        return this.raceTracks.size();
    }
    public int getNextDorsal(int raceTracksSize) throws RaceFullCapacityException {
        if (this.athleteCapacity!= null && raceTracksSize+1 <= this.athleteCapacity)
            return raceTracksSize+1;

        throw new RaceFullCapacityException("Race capacity has been reached. There's no more dorsals available for this race.");
    }

    public boolean isRegistrableStatus() throws RaceFullCapacityException {
        return this.getRaceStatus().ordinal() < RaceStatus.REGISTRATION_CLOSE.ordinal();
    }
}
