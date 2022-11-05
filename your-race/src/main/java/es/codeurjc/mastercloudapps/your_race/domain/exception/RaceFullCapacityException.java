package es.codeurjc.mastercloudapps.your_race.domain.exception;

public class RaceFullCapacityException extends YourRaceException{
    public RaceFullCapacityException(String message){
        super(message);
    }
}
