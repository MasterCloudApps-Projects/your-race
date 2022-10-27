package es.codeurjc.mastercloudapps.your_race;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class UniqueAbstractDatabaseTest extends PostgreSQLContainer<UniqueAbstractDatabaseTest> {


    private static final String IMAGE_VERSION = "postgres:14.5";
    private static UniqueAbstractDatabaseTest container;

    private UniqueAbstractDatabaseTest() {
        super(IMAGE_VERSION);
    }

    public static UniqueAbstractDatabaseTest getInstance() {
        if (container == null) {
            container = new UniqueAbstractDatabaseTest();
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
