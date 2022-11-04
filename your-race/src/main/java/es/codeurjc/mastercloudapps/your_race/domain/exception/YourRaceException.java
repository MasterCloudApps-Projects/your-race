package es.codeurjc.mastercloudapps.your_race.domain.exception;

public abstract class YourRaceException extends Exception {
    protected YourRaceException(String message){
        super(message);
    }
}
