package es.codeurjc.mastercloudapps.your_race.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AthleteAlreadyRegisteredToRaceTest {
    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(AthleteAlreadyRegisteredToRace.class, () -> {
            throw new AthleteAlreadyRegisteredToRace("Athlete not found.");
        });

        String expectedMessage = "Athlete not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}