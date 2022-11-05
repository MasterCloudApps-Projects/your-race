package es.codeurjc.mastercloudapps.your_race.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class TrackRecordTest {

    @Test
    @DisplayName("Can create trackRecord with builder")
    void createTrackRecord() {
        String raceName = "nombre";
        Score score = Score.builder()
                .time(new BigDecimal(10))
                .position(1)
                .build();
        LocalDate date = LocalDate.of(2023, Month.MAY,13);

        TrackRecord trackRecord = TrackRecord.builder()
                .raceName(raceName)
                .score(score)
                .date(date)
                .build();
        
        Assertions.assertSame(trackRecord.getRaceName(), raceName);
        Assertions.assertSame(trackRecord.getScore(), score);
        Assertions.assertSame(trackRecord.getDate(), date);
    }
}