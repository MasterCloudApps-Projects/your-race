package es.codeurjc.mastercloudapps.your_race.domain.mongo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.MongoId;


import java.util.Set;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Organizer {

    @MongoId
    private Long id;

    private String name;

    private Set<Race> organizerRaces;

}
