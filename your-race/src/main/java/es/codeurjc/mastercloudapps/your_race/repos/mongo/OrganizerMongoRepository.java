package es.codeurjc.mastercloudapps.your_race.repos.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.mongo.Organizer;
import es.codeurjc.mastercloudapps.your_race.domain.mongo.Race;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizerMongoRepository extends MongoRepository<Organizer,String>
{
}
