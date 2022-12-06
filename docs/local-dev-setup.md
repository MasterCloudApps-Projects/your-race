# Local Development Setup

Use your favourite IDE. 

## Compilation
mvn -f your-race/pom.xml clean install

## Services setup
cd docker-compose
docker-compose -f docker-compose_sin_app.yml up

## Run your code
mvn -f your-race  spring-boot:run -Dspring-boot.run.arguments=--server.port=8081

## Generate Jib image
mvn -f your-race compile jib:build -Dimage=raquetelio/your-race:v1.3.1