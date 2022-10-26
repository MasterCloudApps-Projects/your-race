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
````
E.g.  
``` http://localhost:8080/api/athletes/12210/applications/12191 ```

### Race registration By Order (with Application code)
```
POST {{baseUrl}}/api/tracks/

Body
{
    "registrationType": "ByOrder",
    "applicationCode": "{applicationCode}"

}
```

### Race registration By Draw (by Organizer) (Pendiente de implementar)
```
POST {{baseUrl}}/api/tracks/

Body
{
    "registrationType": "ByDraw",
    "idAthlete": {idAthlete},
    "idRace": {idRace}

}
```



___
## :es: Documentación de Entrega

### Memoria
[Trabajo Fin de Máster.](/docs/TFM-Memoria-Rafa-Raquel.odt)
Enlace para [verlo en Google Docs.](https://docs.google.com/document/d/17cHzdHlvV2ujh2DzF1rlHlmz_qfKArxPLsnF-EycibQ/edit)

### Presentación
_Pendiente de creación_



