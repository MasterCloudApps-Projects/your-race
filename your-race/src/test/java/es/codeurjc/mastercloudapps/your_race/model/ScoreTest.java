package es.codeurjc.mastercloudapps.your_race.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ScoreTest {

    @Test
    @DisplayName("Can create Score with time and position")
    void createScoreWithTimeAndPosition() {
        BigDecimal time = new BigDecimal(10);
        Integer position = 1;
        Score score = Score.builder()
                .time(time)
                .position(position)
                .build();
        Assertions.assertSame(score.getTime(), time);
        Assertions.assertSame(score.getPosition(), position);
    }
}