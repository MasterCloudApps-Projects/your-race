package es.codeurjc.mastercloudapps.your_race.repos.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.mongo.Track;
import es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.mongo.Race;
import es.codeurjc.mastercloudapps.your_race.repos.TrackYourRaceRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TrackMongoRepository extends MongoRepository<Track,String>, TrackYourRaceRepository {
    Optional<Track> findByAthleteAndRace(Athlete athlete, Race race);
    int countByRace(Race race);
}
