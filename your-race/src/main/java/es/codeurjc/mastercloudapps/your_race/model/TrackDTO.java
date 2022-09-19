package es.codeurjc.mastercloudapps.your_race.model;

import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.Size;


public class TrackDTO {

    private Long id;

    private LocalDateTime registrationDate;

    @Size(max = 255)
    private String status;

    @Valid
    private Score score;

    private Integer dorsal;

    @Size(max = 255)
    private String paymentInfo;

    private Long race;

    private Long athlete;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(final LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(final Score score) {
        this.score = score;
    }

    public Integer getDorsal() {
        return dorsal;
    }

    public void setDorsal(final Integer dorsal) {
        this.dorsal = dorsal;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(final String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public Long getRace() {
        return race;
    }

    public void setRace(final Long race) {
        this.race = race;
    }

    public Long getAthlete() {
        return athlete;
    }

    public void setAthlete(final Long athlete) {
        this.athlete = athlete;
    }

}
