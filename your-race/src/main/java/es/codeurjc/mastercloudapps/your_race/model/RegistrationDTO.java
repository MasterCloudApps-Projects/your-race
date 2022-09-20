package es.codeurjc.mastercloudapps.your_race.model;

import java.time.LocalDateTime;


public class RegistrationDTO {

    private Long id;
    private RegistrationType registrationType;
    private LocalDateTime registrationDate;
    private Double registrationCost;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

}
