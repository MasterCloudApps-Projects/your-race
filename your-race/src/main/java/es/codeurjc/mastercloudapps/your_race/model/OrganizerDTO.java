package es.codeurjc.mastercloudapps.your_race.model;

import javax.validation.constraints.Size;


public class OrganizerDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String suscription;

    @Size(max = 255)
    private String suscriptionStatus;

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

}
