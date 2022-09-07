package es.codeurjc.mastercloudapps.your_race.model;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.Size;


public class TrackRecord {

    @Size(max = 255)
    private String raceName;

    @Valid
    private Score score;

    private LocalDate date;

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(final String raceName) {
        this.raceName = raceName;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(final Score score) {
        this.score = score;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

}
