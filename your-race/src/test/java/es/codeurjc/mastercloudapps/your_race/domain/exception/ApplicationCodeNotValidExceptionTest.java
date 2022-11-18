package es.codeurjc.mastercloudapps.your_race.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationCodeNotValidExceptionTest {
    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(ApplicationCodeNotValidException.class, () -> {
            throw new ApplicationCodeNotValidException("Application code not found.");
        });

        String expectedMessage = "Application code not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}