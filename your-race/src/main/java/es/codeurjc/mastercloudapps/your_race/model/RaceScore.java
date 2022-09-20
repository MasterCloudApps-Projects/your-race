package es.codeurjc.mastercloudapps.your_race.model;

import java.util.List;
import javax.validation.Valid;


public class RaceScore {

    @Valid
    private List<Score> raceScore;

    public List<Score> getRaceScore() {
        return raceScore;
    }

    public void setRaceScore(final List<Score> raceScore) {
        this.raceScore = raceScore;
    }

}
