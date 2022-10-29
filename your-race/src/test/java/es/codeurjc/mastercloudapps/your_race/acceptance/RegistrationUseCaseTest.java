package es.codeurjc.mastercloudapps.your_race.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class RegistrationUseCaseTest {

    @Autowired
    MockMvc mvc;

    @DisplayName("An ahtlete should get a dorsal whel sucessfully registrated in a Race")
    @Test
    public void assignDorsalNumber{

    }
    @DisplayName("An ahtlete should be sucessfully registrated only if there's capacity in the Race")
    @Test
    public void checkRaceCapacity{

    }
}
