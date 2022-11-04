package es.codeurjc.mastercloudapps.your_race.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum RegistrationType {

    BYDRAW("BYDRAW"),
    BYORDER("BYORDER");

    public final String label;





}
