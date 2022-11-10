package es.codeurjc.mastercloudapps.your_race.repos.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.mongo.RegistrationInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationInfoMongoRepository extends MongoRepository<RegistrationInfo,String> {
}
