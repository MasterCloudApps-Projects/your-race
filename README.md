# Your Race

Rafael Gómez Olmedo, Raquel Toscano Marchena.
https://github.com/MasterCloudApps-Projects/your-race

### Overview
Your Race is a scalable platform for managing entry assignment in highly demanded races, like the New York City Marathon or _101 Km de Ronda_ (Spain).


### Artillery

```sh
cd performance
artillery run artilleryTest.yml
```

### Prometheus

### Grafana

Importar Dashboard 9628 para Postgres


## Services
### Athlete race application
```
POST {{baseUrl}}/api/athletes/{idAthlete}}/applications/{idRace}}

E.g: http://localhost:8080/api/athletes/12210/applications/12191 
```

### Race registration By Order (with Application code)
```
POST {{baseUrl}}/api/tracks/byorder

Body
{
    "applicationCode": "{applicationCode}"

}
```

### Race registration By Draw (by Organizer) (Pendiente de implementar)
```
POST {{baseUrl}}/api/tracks/bydraw

Body
{
    "athleteId": {athleteId},
    "raceId": {raceId}

}
```

## Scripts for massive calls generation

These scripts are prepared to produce massive calls for register athletes to a race with previous application.


1. Prepare basic data for race in database.

Adjust the number of applicant athletes and the race capacity in the script database/gererate_registration_calls/1.prepare_basic_data.psql. By default values are set to 100 and 50 respectively.

applicants_num integer := 100;
athelete_capacity integer := 50;

Copy the script in the database container and run it:

```
docker cp database/gererate_registration_calls/1.prepare_basic_data.psql k8s_pgdb_1:/var/lib/postgresql/1.prepare_basic_data.psql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/1.prepare_basic_data.psql 
```
2. Run shell script.
```
bash database/gererate_registration_calls/2.generate_registration_calls.bash
```

3. Run the resulting endpoint calls script.
```
bash 3.massive_registration_calls.bash
```

To start over again and remove the generated data in the database run the script:
```
docker cp database/gererate_registration_calls/_delete_basic_data.sql k8s_pgdb_1:/var/lib/postgresql/_delete_basic_data.sql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/_delete_basic_data.sql 
```

## How to populate some data for local exploratory testing

Copy initializer script to docker container and run script over it:
```
docker cp database/populate_initial_data.sql k8s_pgdb_1:/var/lib/postgresql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/populate_initial_data.sql
```

Check out generated ids in file [database_initial_ids.txt](/database/database_initial_ids.txt).

___
## :es: Documentación de Entrega

### Memoria
[Trabajo Fin de Máster.](/docs/TFM-Memoria-Rafa-Raquel.odt)
Enlace para [verlo en Google Docs.](https://docs.google.com/document/d/17cHzdHlvV2ujh2DzF1rlHlmz_qfKArxPLsnF-EycibQ/edit)

### Presentación
_Pendiente de creación_



