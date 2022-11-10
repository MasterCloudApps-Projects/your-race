package es.codeurjc.mastercloudapps.your_race.domain.sql;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import es.codeurjc.mastercloudapps.your_race.model.TrackRecord;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Set;


@Entity
@TypeDefs(@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Athlete {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String surname;

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private TrackRecord trackRecord;

    @OneToMany(mappedBy = "athlete")
    private Set<Track> athleteTracks;

    @OneToMany(mappedBy = "applicationAthlete")
    private Set<Application> applicationAthleteApplications;

}
