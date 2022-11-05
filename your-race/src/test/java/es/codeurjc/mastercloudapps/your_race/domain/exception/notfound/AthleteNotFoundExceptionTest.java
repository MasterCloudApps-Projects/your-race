package es.codeurjc.mastercloudapps.your_race.domain.exception.notfound;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AthleteNotFoundExceptionTest {
    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(AthleteNotFoundException.class, () -> {
            throw new AthleteNotFoundException("Athlete already registered to race.");
        });

        String expectedMessage = "Athlete already registered to race.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}