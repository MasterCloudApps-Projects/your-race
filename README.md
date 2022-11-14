# Your Race

Rafael Gómez Olmedo, Raquel Toscano Marchena.
https://github.com/MasterCloudApps-Projects/your-race

### Overview
Your Race is a scalable platform for managing entry assignment in highly demanded races, like the New York City Marathon or _101 Km de Ronda_ (Spain).

## K8s Setup + prometheus helm

```sh
minikube start \
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

### Artillery

```sh
cd performance
artillery run artillery{XXX}Test.yml
```

### JMeter

jmeter > [Test Plan.jmx](/performance/Test%20Plan.jmx)

### Grafana

Importar Dashboard:
- 9628 para Postgres
- 4701 Micrometer JVM
- 6417 K8s Cluster
- import [Personalized Dashboard](/grafana/personalized.json)



## Postman Collection
[Postman Collection](/your-race/your-race.postman_collection.json).


# Performance Testing

## Test data

### Import data for testing 
[Import data for testing](/db/export_test_data_20221104/export_202211041741.sql) 

A set of data for Race, Athlete and Applications are populated.

### Application_code list

This list is the source of the massive tests.
 [Application code list of data imported](/db/export_test_data_20221104/application_code_list_202211052018.csv)

### How to generate data for testing
If you want to find out the details of how this data has been generated, check out the documentation in [How to generate data for testing](/docs/how-to-generate-data-for-testing.md) for the Postgres database.

## Artillery
[Artillery scripts and data]](/performance/)

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



