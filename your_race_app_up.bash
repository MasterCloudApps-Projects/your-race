#!/bin/bash

minikube start --cpus 4 --memory 8g

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts 
helm repo add stable https://charts.helm.sh/stable 
helm repo update
helm install prometheus prometheus-community/prometheus

# Instalación de Istio
kubectl apply -f k8s/manifests-operator/

minikube addons enable metrics-server 
minikube addons enable istio
minikube addons enable istio-provisioner 


cd k8s

curl -L https://istio.io/downloadIstio | sh -

cd istio-1.15.3
export PATH=$PWD/bin:$PATH
istioctl install --set profile=demo -y
kubectl label namespace default istio-injection=enabled

kubectl apply -f k8s/istio/

 
 
# Descubrir ip Istio gateway:
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
export INGRESS_HOST=$(minikube ip --profile your-race-k8s-1-24)
echo "http://$INGRESS_HOST:$INGRESS_PORT"



#Get the Prometheus server URL by running these commands in the same shell:
export POD_NAME=$(kubectl get pods --namespace default -l "app=prometheus,component=server" -o jsonpath="{.items[0].metadata.name}")

#Portforward a Prometheus server:
kubectl --namespace default port-forward $POD_NAME 9090 &

# Portforward para la BBDD Postgres
kubectl port-forward service/pgdb 5555:5432 &

# Portforward para Grafana:
kubectl port-forward service/grafana 3000:3000 &


# BBDD Mongo
kubectl apply -f k8s/manifests-mongo/
# Portforward para la BBDD Mongo
kubectl port-forward service/mongodb 27017:27017 &