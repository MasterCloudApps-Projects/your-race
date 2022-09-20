package es.codeurjc.mastercloudapps.your_race.domain;

import es.codeurjc.mastercloudapps.your_race.model.RaceScore;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.CascadeType;


@Entity
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

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private RaceScore raceScore;

    @OneToMany(mappedBy = "race")
    private Set<Track> raceTracks;

    @OneToOne(cascade=CascadeType.ALL)
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

    public Race(String name, String description, LocalDateTime date, String location, double distance, String type, ApplicationPeriod applicationPeriod, Organizer organizer) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.distance = distance;
        this.type = type;
        this.applicationPeriod = applicationPeriod;
        this.organizer = organizer;
    }

    public Race() {
    }
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(final Double distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Integer getAthleteCapacity() {
        return athleteCapacity;
    }

    public void setAthleteCapacity(final Integer athleteCapacity) {
        this.athleteCapacity = athleteCapacity;
    }

    public Set<Track> getRaceTracks() {
        return raceTracks;
    }

    public void setRaceTracks(final Set<Track> raceTracks) {
        this.raceTracks = raceTracks;
    }

    public RaceScore getRaceScore() {
        return raceScore;
    }

    public void setRaceScore(final RaceScore raceScore) {
        this.raceScore = raceScore;
    }

    public ApplicationPeriod getApplicationPeriod() {
        return applicationPeriod;
    }

    public void setApplicationPeriod(final ApplicationPeriod applicationPeriod) {
        this.applicationPeriod = applicationPeriod;
    }

    public Set<Application> getApplicationRaceApplications() {
        return applicationRaceApplications;
    }

    public void setApplicationRaceApplications(final Set<Application> applicationRaceApplications) {
        this.applicationRaceApplications = applicationRaceApplications;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(final Organizer organizer) {
        this.organizer = organizer;
    }

    public Registration getRaceRegistration() {
        return raceRegistration;
    }

    public void setRaceRegistration(final Registration raceRegistration) {
        this.raceRegistration = raceRegistration;
    }

}
