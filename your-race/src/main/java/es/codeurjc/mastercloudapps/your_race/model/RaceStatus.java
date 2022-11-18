package es.codeurjc.mastercloudapps.your_race.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RaceStatus {
    PRE_REGISTRATION_OPEN,
    PRE_REGISTRATION_CLOSE,
    REGISTRATION_OPEN,
    REGISTRATION_CLOSE,
    STARTED,
    FINISHED
}
