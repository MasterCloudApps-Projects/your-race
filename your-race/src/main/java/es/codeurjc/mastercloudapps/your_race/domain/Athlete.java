package es.codeurjc.mastercloudapps.your_race.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import es.codeurjc.mastercloudapps.your_race.model.TrackRecord;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;


@Entity
@TypeDefs(@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class))
public class Athlete {

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

    @Column
    private String surname;

    public Athlete(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Athlete() {
    }

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private TrackRecord trackRecord;

    @OneToMany(mappedBy = "athlete")
    private Set<Track> athleteTracks;

    @OneToMany(mappedBy = "applicationAthlete")
    private Set<Application> applicationAthleteApplications;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public TrackRecord getTrackRecord() {
        return trackRecord;
    }

    public void setTrackRecord(final TrackRecord trackRecord) {
        this.trackRecord = trackRecord;
    }

    public Set<Track> getAthleteTracks() {
        return athleteTracks;
    }

    public void setAthleteTracks(final Set<Track> athleteTracks) {
        this.athleteTracks = athleteTracks;
    }

    public Set<Application> getApplicationAthleteApplications() {
        return applicationAthleteApplications;
    }

    public void setApplicationAthleteApplications(
            final Set<Application> applicationAthleteApplications) {
        this.applicationAthleteApplications = applicationAthleteApplications;
    }

}
