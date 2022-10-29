package es.codeurjc.mastercloudapps.your_race.domain.exception;

public class ApplicationPeriodIsClosedException extends Exception{

    public ApplicationPeriodIsClosedException (String error){
        super(error);
    }

}
