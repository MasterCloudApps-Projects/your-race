package es.codeurjc.mastercloudapps.your_race.model;

import lombok.*;

import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizerDTO {

    private Long id;

    @Size(max = 255)
    private String name;

}
