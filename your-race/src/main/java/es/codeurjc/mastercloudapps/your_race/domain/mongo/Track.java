package es.codeurjc.mastercloudapps.your_race.domain.mongo;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import es.codeurjc.mastercloudapps.your_race.model.Score;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;


import java.time.LocalDateTime;



//@TypeDefs(@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Track {

    @Id
    private String id;

    private String raceId;
    private String athleteId;

    private String raceName;
    private String athleteName;

    private LocalDateTime registrationDate;

    private String status;


    private Score score;


    private Integer dorsal;


    private String paymentInfo;


}
