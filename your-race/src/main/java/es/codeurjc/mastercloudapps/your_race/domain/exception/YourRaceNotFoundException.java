package es.codeurjc.mastercloudapps.your_race.domain.exception;

public abstract class YourRaceNotFoundException extends  Exception {
    public YourRaceNotFoundException(String message){
        super(message);
    }
}
