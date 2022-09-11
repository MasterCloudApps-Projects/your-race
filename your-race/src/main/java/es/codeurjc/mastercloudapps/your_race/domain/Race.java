package es.codeurjc.mastercloudapps.your_race.domain;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;



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
    private double distance;

    @Column
    private String type;

    @OneToMany(mappedBy = "race")
    private Set<Registration> raceRegistrations;

    @OneToOne
    @JoinColumn(name = "application_period_id")
    private ApplicationPeriod applicationPeriod;

    @OneToMany(mappedBy = "applicationRace")
    private Set<Application> applicationRaceApplications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(final double distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Set<Registration> getRaceRegistrations() {
        return raceRegistrations;
    }

    public void setRaceRegistrations(final Set<Registration> raceRegistrations) {
        this.raceRegistrations = raceRegistrations;
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

}
