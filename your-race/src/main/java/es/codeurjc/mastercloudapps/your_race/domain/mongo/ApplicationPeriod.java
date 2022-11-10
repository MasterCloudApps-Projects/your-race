package es.codeurjc.mastercloudapps.your_race.domain.mongo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.MongoId;


import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApplicationPeriod {

    @MongoId
    private Long id;


    private LocalDateTime initialDate;

    private LocalDateTime lastDate;

    private Race race;

    public boolean isOpen(){
        return isValid() && initialDate.isBefore(LocalDateTime.now())
                && LocalDateTime.now().isBefore(lastDate);

    }

    private boolean isValid(){
      return this.initialDate.isBefore(this.lastDate);
    }

}

