package es.codeurjc.mastercloudapps.your_race;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class AbstractDatabaseTest extends PostgreSQLContainer<AbstractDatabaseTest> {


    private static final String IMAGE_VERSION = "postgres:14.5";
    private static AbstractDatabaseTest container;

    private AbstractDatabaseTest() {
        super(IMAGE_VERSION);
    }

    public static AbstractDatabaseTest getInstance() {
        if (container == null) {
            container = new AbstractDatabaseTest();
        }
        return container;
    }

    @Override
    public void start(){
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop(){}

}
