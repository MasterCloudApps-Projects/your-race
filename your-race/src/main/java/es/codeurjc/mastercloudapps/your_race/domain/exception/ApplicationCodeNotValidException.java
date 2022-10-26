package es.codeurjc.mastercloudapps.your_race.domain.exception;

public class ApplicationCodeNotValidException extends Exception{

    public ApplicationCodeNotValidException (String error){
        super(error);
    }
}
