package es.codeurjc.mastercloudapps.your_race.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
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

    @Column
    private String applicationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_race_id")
    private Race applicationRace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_athlete_id")
    private Athlete applicationAthlete;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(final String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public Race getApplicationRace() {
        return applicationRace;
    }

    public void setApplicationRace(final Race applicationRace) {
        this.applicationRace = applicationRace;
    }

    public Athlete getApplicationAthlete() {
        return applicationAthlete;
    }

    public void setApplicationAthlete(final Athlete applicationAthlete) {
        this.applicationAthlete = applicationAthlete;
    }

}
