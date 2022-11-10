package es.codeurjc.mastercloudapps.your_race.domain.sql;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApplicationPeriod {

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
    private LocalDateTime initialDate;

    @Column
    private LocalDateTime lastDate;

    @OneToOne(
            mappedBy = "applicationPeriod",
            fetch = FetchType.LAZY
    )
    private Race race;

    public boolean isOpen(){
        return isValid() && initialDate.isBefore(LocalDateTime.now())
                && LocalDateTime.now().isBefore(lastDate);

    }

    private boolean isValid(){
      return this.initialDate.isBefore(this.lastDate);
    }

}

