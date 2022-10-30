package es.codeurjc.mastercloudapps.your_race.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import es.codeurjc.mastercloudapps.your_race.model.Score;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;


@Entity
@TypeDefs(@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Track {

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

    @Column
    private LocalDateTime registrationDate;

    @Column
    private String status;

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private Score score;

    @Column
    private Integer dorsal;

    @Column
    private String paymentInfo;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "athlete_id", nullable = false)
    private Athlete athlete;

}
