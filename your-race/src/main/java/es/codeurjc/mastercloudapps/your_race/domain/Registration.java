package es.codeurjc.mastercloudapps.your_race.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;
import es.codeurjc.mastercloudapps.your_race.model.Score;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;


@Entity
@TypeDefs(@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class))
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
    private LocalDateTime date;

    @Column
    private String status;

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private Score score;

    @Column
    private Integer dorsal;

    @Column
    @Enumerated(EnumType.STRING)
    private RegistrationType type;

    @Column
    private String paymentInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "athlete_registration_id")
    private Athlete athleteRegistration;

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

    public Race getRace() {
        return race;
    }

    public void setRace(final Race race) {
        this.race = race;
    }

    public Athlete getAthleteRegistration() {
        return athleteRegistration;
    }

    public void setAthleteRegistration(final Athlete athleteRegistration) {
        this.athleteRegistration = athleteRegistration;
    }

}
