package es.codeurjc.mastercloudapps.your_race.domain.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.exception.RaceFullCapacityException;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Race {

    @Id
    private String id;


    private String name;


    private String description;

    private String organizer;


    private LocalDateTime date;


    private String location;


    private Double distance;


    private String type;


    private Integer athleteCapacity;


    private LocalDateTime applicationPeriodInitialDate;
    private LocalDateTime applicationPeriodLastDate;



    private RegistrationType registrationType;
    private LocalDateTime registrationDate;
    private Double registrationCost;
    private Integer concurrentRequestThreshold;


    private boolean nameIsPresent(){
        return Optional.ofNullable(this.name).isPresent();
    }
    private boolean locationIsPresent(){
        return Optional.ofNullable(this.location).isPresent();
    }
    private boolean raceRegistrationIsValid(){

        if (Optional.ofNullable(this.registrationType).isEmpty())
            return true;
        if (this.registrationType.equals(RegistrationType.BYORDER))
            return true;
        return this.registrationType.equals(RegistrationType.BYDRAW);


    }
    private boolean athleteCapacityIsValid(){
        return Optional.ofNullable(this.athleteCapacity).isEmpty()
                || athleteCapacity.compareTo(0) > 0;
    }
    private boolean distanceIsValid(){
        if (Optional.ofNullable(this.distance).isEmpty())
                return true;
        return this.distance > 0;


    }
    private boolean concurrentThresholdIsValid()
    {

        if (Optional.ofNullable(this.concurrentRequestThreshold).isEmpty())
            return true;
        return this.concurrentRequestThreshold > 1;
    }

    private boolean registrationDateIsValid(){

        if (Optional.ofNullable(this.registrationDate).isEmpty())
            return true;
        return this.registrationDate.isAfter(LocalDateTime.now());
    }

    private boolean applicationPeriodIsValid(){

        if (Optional.ofNullable(this.applicationPeriodInitialDate).isEmpty()
        && Optional.ofNullable(this.applicationPeriodLastDate).isEmpty())
            return true;
        return Optional.ofNullable(this.applicationPeriodInitialDate).isPresent()
                && Optional.ofNullable(this.applicationPeriodLastDate).isPresent()
                && this.applicationPeriodInitialDate.isBefore(this.applicationPeriodLastDate);
    }
    private boolean datesAreValid(){

       if (Optional.ofNullable(this.registrationDate).isEmpty())
            return true;
        if (Optional.ofNullable(this.applicationPeriodInitialDate).isEmpty()
                && Optional.ofNullable(this.applicationPeriodLastDate).isEmpty())
            return true;
        return this.registrationDate.isAfter(this.applicationPeriodLastDate);
    }
    public boolean isValid() {
        return  nameIsPresent()
                && locationIsPresent()
                && raceRegistrationIsValid()
                && athleteCapacityIsValid()
                && distanceIsValid()
                && concurrentThresholdIsValid()
                && registrationDateIsValid()
                && applicationPeriodIsValid()
                && datesAreValid();
    }

    public boolean isOpen(){
      return LocalDateTime.now().isBefore(this.date);
    }

      public int getNextDorsal(int raceTracksSize) throws RaceFullCapacityException {
        if (this.athleteCapacity!= null && raceTracksSize+1 <= this.athleteCapacity)
            return raceTracksSize+1;

        throw new RaceFullCapacityException("Race capacity has been reached. There's no more dorsals available for this race.");

    }



    public boolean isOpenApplicationPeriod(){
        return isValidApplicationPeriod() && applicationPeriodInitialDate.isBefore(LocalDateTime.now())
                && LocalDateTime.now().isBefore(applicationPeriodLastDate);

    }

    private boolean isValidApplicationPeriod(){
        return this.applicationPeriodInitialDate.isBefore(this.applicationPeriodLastDate);
    }
}
