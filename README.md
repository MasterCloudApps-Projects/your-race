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

Importar Dashboard: 
- 9628 para Postgres
- 4701 Micrometer JVM
- 6417 K8s Cluster
- 


## K8s Setup

Para ejecutar el conjunto de servicios se ha creado un paquete de manifiestos Kubernetes que se encarga de levantar todo el stack, Bases de datos, RabbitMQ y las 4 aplicaciones, pasándose las rutas de acceso y credenciales como variables de entorno.

Es necesario tener levantado Minikube:

```sh
minikube start --cpus 4 --memory 16g
```

```sh
kubectl apply -f k8s/manifests/
```

## K8s Setup + prometheus helm

```sh
minikube delete && minikube start \
--cpus 4 --memory 16g \

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add stable https://charts.helm.sh/stable
helm repo update
helm install prometheus prometheus-community/prometheus
```
Reference: [1] 

```sh
kubectl apply -f k8s/manifests-operator/
```

## Instalación de istio:

```sh
minikube addons enable metrics-server
minikube addons enable istio-provisioner
minikube addons enable istio


cd k8s
curl -L https://istio.io/downloadIstio | sh -
cd istio-1.15.3
export PATH=$PWD/bin:$PATH
istioctl install --set profile=demo -y
kubectl label namespace default istio-injection=enabled
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


## Services

Find bellow the description of the most relevant services.
You can import your-race.postman_collection.json to get the full list of implemented services. 
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


## How to generate data for Testing
### Massive calls generation in one script

Run script [generate_and_send_registration_calls.bash](/db/gererate_registration_calls/generate_and_send_registration_calls.bash) to send massive calls to register athletes to a race:

```
bash db/gererate_registration_calls/generate_and_send_registration_calls.bash
```

You can set the number of applicant athletes and the race capacity by editing the script [1.prepare_basic_data.psql](/db/gererate_registration_calls/1.prepare_basic_data.psql).



### Massive calls generation step by step

#### [Optional] Remove generated test data if you have previously run the scripts
```
docker cp db/gererate_registration_calls/_delete_basic_data.sql k8s_pgdb_1:/var/lib/postgresql/_delete_basic_data.sql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/_delete_basic_data.sql 
```

#### 1. Prepare basic data for race in database.

This script creates an Organizer, a Race (with ApplicationPeriod, RegistrationRace and the capacity set in the parameter 'athlete_capacity') and as many Ahtletes and Applications to the race as set in parameter 'athlete_applications'.

```
docker cp db/gererate_registration_calls/1.prepare_basic_data.psql k8s_pgdb_1:/var/lib/postgresql/1.prepare_basic_data.psql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/1.prepare_basic_data.psql 
``` 

#### 2. Generate the registration calls.

This script generates a file with the calls to the endpoint for register the Athletes in the Race (ByOrder) with their applicationCodes. 
```
REGISTRATION_CALLS_FILE_NAME="performance/test/massive_registration_calls-"$(date +"%Y-%m-%d-%H-%M-%s".bash)
bash db/gererate_registration_calls/2.generate_registration_calls.bash $REGISTRATION_CALLS_FILE_NAME
```

#### 3. Run the resulting script with the registration calls.

Run the script and track the time spent in the process of the file through 'SECONDS' shell variable. Results are saved in the '*result.txt' file.


```
SECONDS=0
bash $REGISTRATION_CALLS_FILE_NAME
echo "Duration: $SECONDS seconds ("$(( SECONDS / 60 )) "minutes)("$(( SECONDS / 3060 )) "hours)" > ${REGISTRATION_CALLS_FILE_NAME}_result.txt
echo `cat ${REGISTRATION_CALLS_FILE_NAME}_result.txt`
```

### How to populate some data for local exploratory testing

Copy initializer script to docker container and run script over it:
```
docker cp db/populate_initial_data.sql k8s_pgdb_1:/var/lib/postgresql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/populate_initial_data.sql
```

Check out generated ids in file [database_initial_ids.txt](/db/database_initial_ids.txt).


# References

[1] https://refactorizando.com/autoescalado-prometheus-spring-boot-kubernetes/
https://jschmitz.dev/posts/testcontainers_how_to_use_them_in_your_spring_boot_integration_tests/

___
## :es: Documentación de Entrega

### Memoria
[Trabajo Fin de Máster.](/docs/TFM-Memoria-Rafa-Raquel.odt)
Enlace para [verlo en Google Docs.](https://docs.google.com/document/d/17cHzdHlvV2ujh2DzF1rlHlmz_qfKArxPLsnF-EycibQ/edit)

### Presentación
_Pendiente de creación_



