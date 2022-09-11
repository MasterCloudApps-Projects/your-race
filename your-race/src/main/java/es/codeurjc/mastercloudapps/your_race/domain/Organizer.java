package es.codeurjc.mastercloudapps.your_race.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


@Entity
public class Organizer {

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
    private String name;

    @Column
    private String suscription;

    @Column
    private String suscriptionStatus;

    @OneToMany(mappedBy = "organizer")
    private Set<Race> organizerRaces;

    public Organizer(String name, String suscription, String suscriptionStatus) {
        this.name = name;
        this.suscription = suscription;
        this.suscriptionStatus = suscriptionStatus;
    }

    public Organizer() {

    }

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

    public String getSuscription() {
        return suscription;
    }

    public void setSuscription(final String suscription) {
        this.suscription = suscription;
    }

    public String getSuscriptionStatus() {
        return suscriptionStatus;
    }

    public void setSuscriptionStatus(final String suscriptionStatus) {
        this.suscriptionStatus = suscriptionStatus;
    }

    public Set<Race> getOrganizerRaces() {
        return organizerRaces;
    }

    public void setOrganizerRaces(final Set<Race> organizerRaces) {
        this.organizerRaces = organizerRaces;
    }

}
