package es.codeurjc.mastercloudapps.your_race.domain.exception.notfound;

public abstract class YourRaceNotFoundException extends  Exception {
    protected YourRaceNotFoundException(String message){
        super(message);
    }
}
