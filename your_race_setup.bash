#!/bin/bash

echo "Starting your-race setup..."

minikube start --cpus 4 --memory 8g

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add stable https://charts.helm.sh/stable
helm repo update

helm install prometheus prometheus-community/prometheus
kubectl apply -f k8s/manifests-operator/

cd k8s
curl -L https://istio.io/downloadIstio | sh -


cd istio-1.15.3
export PATH=$PWD/bin:$PATH
istioctl install --set profile=demo -y
kubectl label namespace default istio-injection=enabled

cd ..
kubectl apply -f k8s/istio/

#Get the Prometheus server URL by running these commands in the same shell:
export POD_NAME=$(kubectl get pods --namespace default -l "app=prometheus,component=server" -o jsonpath="{.items[0].metadata.name}")
#Portforward a Prometheus server:
kubectl --namespace default port-forward $POD_NAME 9090 &

#Portforward para la BBDD
kubectl port-forward service/pgdb 5555:5432 &

# Portforward para Grafana:
kubectl port-forward service/grafana 3000:3000 &

# Importar los datos de test performance a la BBDD:
psql postgresql://admin:admin@localhost:5555/racedb -f db/postgres/export_test_data_20221104/export_202211041741.sql


# BBDD Mongo
kubectl apply -f k8s/manifests-mongo/
# Portforward para la BBDD Mongo
kubectl port-forward service/mongodb 27017:27017 &
# Importar los datos de test performance a la BBDD Mongo:
#sh db/mongo/mongo_import.bash racedb
# Generar los datos:
mongosh -f --username root --password password -f   delete_and_generate_basic_data.js --shell




#Descubrir ip Istio gateway:
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
export INGRESS_HOST=$(minikube ip)
echo "Istio gateway ingress port:"
echo "http://$INGRESS_HOST:$INGRESS_PORT"

# Watch cluster deployments, services,..
watch -n 1 kubectl get pod,deployment,service,horizontalpodautoscaler,ingresses,destinationrule,virtualservice &


echo "done"