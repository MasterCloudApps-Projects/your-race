# Performance tests Postgres VS Mongo

The existing code with JPA and Postgres persistence was adapted to run a Spike and check the performance test results with Mongo DB. 
The results were pretty similar to the ones provided by Postgres, so therefore this experiment was discarded. 

This experiment can be run through the Feature Toggle "Use MongoDB".


## How to test in Local dev
### Branch
```
fe-mongo-with-feature-toggles 
```

(Bear in mind this is a experimental code, you'll find lots of Code Smells there :unamused:)


### Feature Toggle 

Enable "Use MongoDB".
```
http://localhost/togglz-console/index
```

### Run a Mongo instance
```
sudo docker run --rm -p 27017:27017 -d mongo:latest   
``` 

### Access to Mongo
```
mongodb://localhost:27017
```

## Spripts

### Generate basic data for testing:
```
mongosh -f db/mongo/delete_and_generate_basic_data.js --shell
```

#### Export the basic data for testing:
```
mongoexport --forceTableScan  --collection=race  --collection=athlete  --collection=application --db=racedb --out=racedb-test-data.json
```

#### Import the basic data for testing:
Run script:
````
mongo_import.bash 
````
You can also import the basic data through UI of MongoDBCompass.


Finally, test with Postman or Artillery for massive calls.


## How to test in Kubernetes cluster

### Docker Image
raquetelio/your-race:v20221113

### Feature Toggle 

Enable "Use MongoDB".
```
http://$INGRESS_HOST:$INGRESS_PORT/togglz-console/index
```

### Setup

Setup Kubernetes cluster according to ![README.md](/README.md).


Apply your-race deployment provided in branch fe-mongo-with-feature-toggles:
```
kubectl apply -f k8s/manifests-operator/your-race-deployment.yaml
```

Execute Mongo manifests:
```
kubectl apply -f k8s/manifests-mongo
```

Portforward:

```
kubectl port-forward service/mongodb 27017:27017 &
```


Access
```
mongodb://root:password@$INGRESS_HOST:27017
```

## Tests

### Test data

Generate data directly on pod:
```
mongosh -f --username root --password password -f   delete_and_generate_basic_data.js --shell
```

### Run tests:
artillery run artilleryRaceRegistration.yml > results/artilleryRaceRegistration_result_TestX_$(date +"%Y-%m-%d-%H-%M-%s".txt) 

[File with application codes](/performance/application_code_mongo_20221113.csv "File with application codes")


## Results:

[Results folder](/performance/raquetelio/results/mongo)

[Full detail of test cases (from 1 to 24)](https://docs.google.com/spreadsheets/d/1K2KCRoR6Kmkq3UN-WFXWZY6JZzejWN9V1HZ-6EVJA_Q/edit#gid=1368903262 "Full detail of test cases (from 1 to 24)")

### Graphic

[Tests graphic postgres-mongo](/performance/raquetelio/results/tests-graphic-postgres-mongo.jpg "Tests graphic postgres-mongo")



## Work not done
If this feature were interesting in the future, there are some work to do before continuing it:

- MongoDB indexes -> Should be added in the entities definition. Some where added directly in the test phase, like Athlete and Race for Application. It wasn't a big improvement in the results.

- Code should be refactored -> Adding some behavioral patterns such Visitor, might solve the issue with the duplicated code.

- Some endpoints are not implemented to work in Mongo. (There are only implementations for endpoints to get a race registration: create a Race, create an Athlete, create an Application, and finally, Register By Order). Only all the endpoints for Athlete Resource were implemented for learning purposes.

- Consider future line of investigation. Such instead of using Postgres VS Mongo, using a Hybrid solution for using Mongo only for Registrations in the RegistrationDate of a race. At the moment we didn't find any positive reason for implementing this.

### Final comments
While working on this feature, I was able to struggle in the heritance of the Domain classes in orther to provide subclasess for each persistence technology (the pain point was how to define the entities for JPA without duplicating the attributes of the parent class).
In addition, I found that Lombok has issues with providing the Builder pattern in heritance, so I had to provide them manually. This line of work was discarded because tests were failing and it wasn't worthit to work further on this premises.



