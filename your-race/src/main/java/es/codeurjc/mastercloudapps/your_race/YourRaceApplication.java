package es.codeurjc.mastercloudapps.your_race;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class YourRaceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(YourRaceApplication.class, args);
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable("raceByOrderCreationProgressNotifications")
                .build();
    }
    /*@Bean
    public Queue myQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-queue-type", "stream");
        return new Queue("raceByOrderCreationProgressNotifications", true, false, false, args);
    }*/
}
