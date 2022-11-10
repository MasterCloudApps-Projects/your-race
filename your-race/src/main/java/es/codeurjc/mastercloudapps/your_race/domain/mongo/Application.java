package es.codeurjc.mastercloudapps.your_race.domain.mongo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.MongoId;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Application {

    @MongoId
    String id;

    private String applicationCode;

    @ToString.Exclude
    private Race applicationRace;

    @ToString.Exclude
    private Athlete applicationAthlete;

}
