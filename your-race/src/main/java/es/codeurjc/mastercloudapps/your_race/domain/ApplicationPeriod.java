package es.codeurjc.mastercloudapps.your_race.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class ApplicationPeriod {

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
    private LocalDateTime initialDate;

    @Column
    private LocalDateTime lastDate;

    @OneToOne(
            mappedBy = "applicationPeriod",
            fetch = FetchType.LAZY
    )
    private Race applicationPeriod;

    public ApplicationPeriod(LocalDateTime initialDate, LocalDateTime lastDate) {
        this.initialDate = initialDate;
        this.lastDate = lastDate;
    }

    public ApplicationPeriod() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(final LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDateTime getLastDate() {
        return lastDate;
    }

    public void setLastDate(final LocalDateTime lastDate) {
        this.lastDate = lastDate;
    }

    public Race getApplicationPeriod() {
        return applicationPeriod;
    }

    public void setApplicationPeriod(final Race applicationPeriod) {
        this.applicationPeriod = applicationPeriod;
    }

}
