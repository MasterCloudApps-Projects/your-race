# YourRace

This app was created with Bootify.io - more documentation [can be found here](https://bootify.io/docs/). Feel free to contact us for further questions.

## Development

During development it is recommended to use the profile `local`. In IntelliJ, `-Dspring.profiles.active=local` can be added in the VM options of the Run Configuration after enabling this property in "Modify options".

Update your local database connection in `application.properties` or create your own `application-local.properties` file to override settings for development.

Lombok must be supported by your IDE. For this, in IntelliJ install the Lombok plugin and enable annotation processing - [learn more](https://bootify.io/intellij/spring-boot-with-lombok.html).

After starting the application it is accessible under `localhost:8080`.

## Build Image

Jib:
```sh
mvn compile jib:build -f your-race -Dimage=rafarex70/your-race:$TAG
```

DockerFile:
```sh
docker build -f .Dockerfile -t rafarex70/your-race .
```

## Build

The application can be built using the following command:

```sh
mvnw clean package
```

The application can then be started with the following command - here with the profile `production`:

```sh
java -Dspring.profiles.active=production -jar ./target/your-race-0.0.1-SNAPSHOT.jar
```

## Services

Find bellow the description of the most relevant services. You can import your-race.postman_collection.json to get the full list of implemented services. 
### Get Races

Use 'open=true' parameter to get only open races (not celebrated yet).

```
GET {{baseUrl}}/api/races?open=true
http://localhost:8080/api/races?open=true 
```

### Get Athletes
```
GET {{baseUrl}}/api/athletes
http://localhost:8080/api/atheletes
```

### Athlete Race Application

#### Create Application to a Race:
```
POST {{baseUrl}}/api/athletes/{idAthlete}}/applications/{idRace}}
http://localhost:8080/api/athletes/10004/applications/10003 
```

#### Get Applications for a Race:

```
GET {{baseUrl}}/api/applications/races/{idRace}}
http://localhost:8080/api/applications/races/10003
```

### Race Registrations
#### Race Registration By Order (with Application code)
```
POST {{baseUrl}}/api/tracks/byorder
Body: {"applicationCode": "{applicationCode}"}

curl -X POST http://localhost:8080/api/tracks/byorder -H "content-type: application/json" -d '{ "applicationCode":  "d0f3529f4"}'
```

#### Race Registration By Draw (by Organizer)
```
POST {{baseUrl}}/api/tracks/bydraw
Body: {"athleteId": {athleteId},"raceId": {raceId}}

curl -X POST http://localhost:8080/api/tracks/bydraw -H "content-type: application/json" -d '{"athleteId": 10006,"raceId": 10003}'
```

#### Get Registrations for an open Race
```
GET {{baseUrl}}/api/tracks?open=true
Body: {"raceId": {raceId}}

curl -X GET Http://localhost:8080/api/races?open=true -H "content-type: application/json" -d '{"raceId": 10003}'
```

#### Get Registrations for an Athlete to a Race 
```
GET {{baseUrl}}/api/tracks?open=true
Body: {"raceId": {raceId}, "athleteId": {athleteId}}

curl -X GET Http://localhost:8080/api/races?open=true -H "content-type: application/json" -d '{"raceId": 10003, "athleteId": 10010}'
```

## Further readings

* [Maven docs](https://maven.apache.org/guides/index.html)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)  



