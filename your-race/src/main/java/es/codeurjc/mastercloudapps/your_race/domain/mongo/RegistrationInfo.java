package es.codeurjc.mastercloudapps.your_race.domain.mongo;

import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.MongoId;


import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegistrationInfo {


    @MongoId
    private String id;


    private RegistrationType registrationType;


    private LocalDateTime registrationDate;


    private Double registrationCost;


    private Integer concurrentRequestThreshold;

}
