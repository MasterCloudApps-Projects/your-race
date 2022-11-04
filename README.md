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


## K8s Setup

Para ejecutar el conjunto de servicios se ha creado un paquete de manifiestos Kubernetes que se encarga de levantar todo el stack, Bases de datos, RabbitMQ y las 4 aplicaciones, pasándose las rutas de acceso y credenciales como variables de entorno.

Es necesario tener levantado Minikube:

```sh
minikube start --cpus 6 --memory 16g
minikube addons enable metrics-server
minikube addons enable istio
minikube addons enable istio-provisioner
```

Instalación de istio:

```sh
curl -L https://istio.io/downloadIstio | sh -
cd istio-1.15.2
export PATH=$PWD/bin:$PATH
istioctl install --set profile=demo -y
kubectl label namespace default istio-injection=enabled
```

## K8s Setup + prometheus operator

```sh
minikube delete && minikube start \
--cpus 6 --memory 16g \
--bootstrapper=kubeadm \
--extra-config=kubelet.authentication-token-webhook=true \
--extra-config=kubelet.authorization-mode=Webhook \
--extra-config=scheduler.bind-address=0.0.0.0 \
--extra-config=controller-manager.bind-address=0.0.0.0

cd k8s/manifests-operator
git clone https://github.com/prometheus-operator/kube-prometheus.git
cd kube-prometheus
kubectl apply --server-side -f manifests/setup
until kubectl get servicemonitors --all-namespaces ; \
do date; sleep 1; echo ""; done
kubectl create -f manifests/
```

## K8s Deploy

```sh
kubectl apply -f k8s/manifests/
```

```sh
kubectl apply -f k8s/manifests-operator/
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



___
## :es: Documentación de Entrega

### Memoria
[Trabajo Fin de Máster.](/docs/TFM-Memoria-Rafa-Raquel.odt)
Enlace para [verlo en Google Docs.](https://docs.google.com/document/d/17cHzdHlvV2ujh2DzF1rlHlmz_qfKArxPLsnF-EycibQ/edit)

### Presentación
_Pendiente de creación_



