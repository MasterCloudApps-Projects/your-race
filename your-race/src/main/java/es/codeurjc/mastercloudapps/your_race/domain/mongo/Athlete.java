package es.codeurjc.mastercloudapps.your_race.domain.mongo;


import es.codeurjc.mastercloudapps.your_race.model.TrackRecord;
import lombok.*;

import org.springframework.data.annotation.Id;


import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Athlete {

    @Id
    private String id;

    private String name;

    private String surname;


    private TrackRecord trackRecord;

    private Set<Track> athleteTracks;

    private Set<Application> applicationAthleteApplications;

}
