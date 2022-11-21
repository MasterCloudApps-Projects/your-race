# Local Development Setup



Use your favourite ID.


Compilation:
mvn -f your-race/pom.xml clean install


How to test:
cd docker-compose
docker-compose up 

falta RAbbitMQ
docker run --rm -p 5672:5672 -p 15672:15672 rabbitmq:3-management

Run your code:
mvn -f your-race  spring-boot:run -Dspring-boot.run.arguments=--server.port=8081

Generate Jib image