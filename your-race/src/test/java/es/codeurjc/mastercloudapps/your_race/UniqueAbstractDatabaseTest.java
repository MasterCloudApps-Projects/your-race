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
@ContextConfiguration(initializers = UniqueAbstractDatabaseTest.Initializer.class)
public class UniqueAbstractDatabaseTest extends PostgreSQLContainer<UniqueAbstractDatabaseTest> {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14.5")
            .withDatabaseName("test")
            .withUsername("admin")
            .withPassword("admin");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    public static PostgreSQLContainer getInstance(){
        if(postgreSQLContainer == null){
            postgreSQLContainer = new UniqueAbstractDatabaseTest();
        }
        return  postgreSQLContainer;
    }

    @Override
    public void start(){
        super.start();
    }

    @Override
    public void stop(){}

}
