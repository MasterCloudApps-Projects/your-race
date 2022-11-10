package es.codeurjc.mastercloudapps.your_race.repos.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.mongo.Track;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackMongoRepository extends MongoRepository<Track,String> {
}
