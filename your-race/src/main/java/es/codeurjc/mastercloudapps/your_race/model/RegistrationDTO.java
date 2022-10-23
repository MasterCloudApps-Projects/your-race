package es.codeurjc.mastercloudapps.your_race.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "registrationType")   // field on which we differentiate objects
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegistrationByOrderDTO.class, name = "ByOrder"),  // if value of 'type' field equals to 'car' instantiate a Car object
        @JsonSubTypes.Type(value = RegistrationByDrawDTO.class, name = "ByDrawing")  // if value of 'type' field equals to 'airplane' instantiate an Airplane object
})
public abstract class RegistrationDTO {

    private RegistrationType registrationType;

    public RegistrationType getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(RegistrationType registrationType) {
        this.registrationType = registrationType;
    }
}
