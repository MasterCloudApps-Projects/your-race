package es.codeurjc.mastercloudapps.your_race.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;


public class Score {

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal time;

    private Integer position;

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(final BigDecimal time) {
        this.time = time;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(final Integer position) {
        this.position = position;
    }

}
