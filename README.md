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

## How to populate data for local testing

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



