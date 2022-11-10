package es.codeurjc.mastercloudapps.your_race.repos.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.mongo.ApplicationPeriod;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationPeriodMongoRepository extends MongoRepository<ApplicationPeriod, String> {
}
