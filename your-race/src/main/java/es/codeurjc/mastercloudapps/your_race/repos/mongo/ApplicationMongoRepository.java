package es.codeurjc.mastercloudapps.your_race.repos.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.mongo.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ApplicationMongoRepository extends MongoRepository<Application,String> {
    Optional<Application> findByApplicationCode(String applicationCode);
}
