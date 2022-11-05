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

### JMeter

```sh
cd performance
```
jmeter > Test Plan.jmx

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

## Massive calls generation

Run this script to send massive calls for register athletes to a race with previous application: [generate_and_send_registration_calls.bash](/db/gererate_registration_calls/generate_and_send_registration_calls.bash)

```
bash /db/gererate_registration_calls/generate_and_send_registration_calls.bash
``` 

You can set the number of applicant athletes and the race capacity editing the script [1.prepare_basic_data.psql](/db/gererate_registration_calls/1.prepare_basic_data.psql).


## K8s Setup

Para ejecutar el conjunto de servicios se ha creado un paquete de manifiestos Kubernetes que se encarga de levantar todo el stack, Bases de datos, RabbitMQ y las 4 aplicaciones, pasándose las rutas de acceso y credenciales como variables de entorno.

Es necesario tener levantado Minikube:

```sh
minikube start --cpus 6 --memory 16384
minikube addons enable metrics-server
minikube addons enable istio
minikube addons enable istio-provisioner
```

Instalación de istio:

```sh
cd k8s
curl -L https://istio.io/downloadIstio | sh -
cd istio-1.15.2
export PATH=$PWD/bin:$PATH
istioctl install --set profile=demo -y
kubectl label namespace default istio-injection=enabled
```


## K8s Deploy

```sh
kubectl apply -f k8s/manifests/
```

Despliegue Istio gateway

```sh
kubectl apply -f k8s/istio/
```


## How to test

Descubrir ip Istio gateway:

```sh
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
export INGRESS_HOST=$(minikube ip)
echo "http://$INGRESS_HOST:$INGRESS_PORT"
```

## How to populate some data for local exploratory testing

Copy initializer script to docker container and run script over it:
```
docker cp db/populate_initial_data.sql k8s_pgdb_1:/var/lib/postgresql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/populate_initial_data.sql
```

Check out generated ids in file [database_initial_ids.txt](/db/database_initial_ids.txt).

___
## :es: Documentación de Entrega

### Memoria
[Trabajo Fin de Máster.](/docs/TFM-Memoria-Rafa-Raquel.odt)
Enlace para [verlo en Google Docs.](https://docs.google.com/document/d/17cHzdHlvV2ujh2DzF1rlHlmz_qfKArxPLsnF-EycibQ/edit)

### Presentación
_Pendiente de creación_



