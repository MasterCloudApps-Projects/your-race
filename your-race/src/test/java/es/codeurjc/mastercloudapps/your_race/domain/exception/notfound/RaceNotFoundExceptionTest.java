package es.codeurjc.mastercloudapps.your_race.domain.exception.notfound;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RaceNotFoundExceptionTest {

    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(RaceNotFoundException.class, () -> {
            throw new RaceNotFoundException("Race not found.");
        });

        String expectedMessage = "Race not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}