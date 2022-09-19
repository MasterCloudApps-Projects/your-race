package es.codeurjc.mastercloudapps.your_race.model;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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

    private RegistrationType registrationType;

    private LocalDateTime registrationDate;

    private Double registrationCost;

    private Integer athleteCapacity;

    private Long applicationPeriod;

    @NotNull
    private Long organizer;

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

    public RegistrationType getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(final RegistrationType registrationType) {
        this.registrationType = registrationType;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(final LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Double getRegistrationCost() {
        return registrationCost;
    }

    public void setRegistrationCost(final Double registrationCost) {
        this.registrationCost = registrationCost;
    }

    public Integer getAthleteCapacity() {
        return athleteCapacity;
    }

    public void setAthleteCapacity(final Integer athleteCapacity) {
        this.athleteCapacity = athleteCapacity;
    }

    public Long getApplicationPeriod() {
        return applicationPeriod;
    }

    public void setApplicationPeriod(final Long applicationPeriod) {
        this.applicationPeriod = applicationPeriod;
    }

    public Long getOrganizer() {
        return organizer;
    }

    public void setOrganizer(final Long organizer) {
        this.organizer = organizer;
    }

}
