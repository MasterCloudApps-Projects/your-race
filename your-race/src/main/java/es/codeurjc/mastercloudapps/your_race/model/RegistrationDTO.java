package es.codeurjc.mastercloudapps.your_race.model;

import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.Size;


public class RegistrationDTO {

    private Long id;

    private LocalDateTime date;

    @Size(max = 255)
    private String status;

    @Valid
    private Score score;

    private Integer dorsal;

    private RegistrationType type;

    @Size(max = 255)
    private String paymentInfo;

    private Long registration;

    private Long athleteRegistration;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
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

    public RegistrationType getType() {
        return type;
    }

    public void setType(final RegistrationType type) {
        this.type = type;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(final String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public Long getRegistration() {
        return registration;
    }

    public void setRegistration(final Long registration) {
        this.registration = registration;
    }

    public Long getAthleteRegistration() {
        return athleteRegistration;
    }

    public void setAthleteRegistration(final Long athleteRegistration) {
        this.athleteRegistration = athleteRegistration;
    }

}
