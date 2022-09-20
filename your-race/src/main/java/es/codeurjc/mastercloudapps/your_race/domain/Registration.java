package es.codeurjc.mastercloudapps.your_race.domain;

import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class Registration {

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
    @Enumerated(EnumType.STRING)
    private RegistrationType registrationType;

    @Column
    private LocalDateTime registrationDate;

    @Column
    private Double registrationCost;

    @OneToOne(
            mappedBy = "raceRegistration",
            fetch = FetchType.LAZY
    )
    private Race raceRegistration;

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

    public Race getRaceRegistration() {
        return raceRegistration;
    }

    public void setRaceRegistration(final Race raceRegistration) {
        this.raceRegistration = raceRegistration;
    }

}
