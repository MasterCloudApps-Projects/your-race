package es.codeurjc.mastercloudapps.your_race.repos.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.mongo.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationMongoRepository extends MongoRepository<Application,String> {
}
