package es.codeurjc.mastercloudapps.your_race.repos.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.mongo.Race;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RaceMongoRepository extends MongoRepository<Race,String>
{
}
