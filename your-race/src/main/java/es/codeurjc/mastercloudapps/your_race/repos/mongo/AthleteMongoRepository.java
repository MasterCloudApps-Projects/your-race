package es.codeurjc.mastercloudapps.your_race.repos.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AthleteMongoRepository extends MongoRepository<Athlete,String> {
}
