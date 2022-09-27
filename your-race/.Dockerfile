FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /project
COPY /src /project/src
COPY pom.xml /project/
RUN mvn -B package -Dmaven.test.skip=true
# Imagen base para el contenedor de la aplicación
FROM openjdk:17-jdk-slim
WORKDIR /usr/src/app/
COPY --from=builder /project/target/*.jar /usr/src/app/
CMD [ "java", "-jar", "your-race-0.0.1-SNAPSHOT.jar" ]