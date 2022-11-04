package es.codeurjc.mastercloudapps.your_race.domain.exception;

public class ApplicationCodeNotValidException extends YourRaceException{

    public ApplicationCodeNotValidException (String error){
        super(error);
    }
}
