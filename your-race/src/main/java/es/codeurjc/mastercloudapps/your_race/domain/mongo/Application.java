package es.codeurjc.mastercloudapps.your_race.domain.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Application {

    @Id
    private String id;

    private String raceId;
    private String athleteId;

    private String raceName;
    private String athleteName;

    private String applicationCode;


}
