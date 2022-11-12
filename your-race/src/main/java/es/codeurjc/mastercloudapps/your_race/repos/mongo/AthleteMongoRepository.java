package es.codeurjc.mastercloudapps.your_race.repos.mongo;

import es.codeurjc.mastercloudapps.your_race.domain.mongo.Athlete;
import es.codeurjc.mastercloudapps.your_race.domain.sql.Application;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AthleteMongoRepository extends MongoRepository<Athlete,String> {

   //
    // Optional<Athlete> findBy_id(ObjectId _id);
}
