package es.codeurjc.mastercloudapps.your_race.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class AthleteDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surname;

    @Valid
    private TrackRecord trackRecord;

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

}
