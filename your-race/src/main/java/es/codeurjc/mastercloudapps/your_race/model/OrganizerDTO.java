package es.codeurjc.mastercloudapps.your_race.model;

import javax.validation.constraints.Size;

import lombok.*;


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
