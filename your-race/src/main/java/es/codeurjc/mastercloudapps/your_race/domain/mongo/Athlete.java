package es.codeurjc.mastercloudapps.your_race.domain.mongo;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import es.codeurjc.mastercloudapps.your_race.model.TrackRecord;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Athlete {

    @MongoId
    private String id;


    private String name;

    private String surname;


    private TrackRecord trackRecord;

    private Set<Track> athleteTracks;

    private Set<Application> applicationAthleteApplications;

}
