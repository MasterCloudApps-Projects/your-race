# How to run a test

## 1. Setup 

### 1.1. Start minikube

Start minikube (from a new cluster or a stopped one).
```
minikube start --cpus 4 --memory 8g
```

### 1.2. Additional installs for a new cluster
#### Prometheus

First time, install helm prometheus in your local
```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add stable https://charts.helm.sh/stable
```

Update and install helm prometheus:
```
helm repo update
helm install prometheus prometheus-community/prometheus
```

#### Install your-race services
```
kubectl apply -f k8s/manifests-operator/
```

#### Istio
First time, download Istio in your local.
```
cd k8s
curl -L https://istio.io/downloadIstio | sh -
cd ..
```
Install Istio. This action takes a little bit of more time
```
export PATH=$PWD/k8s/istio-1.16.0/bin:$PATH
istioctl install --set profile=demo -y
kubectl label namespace default istio-injection=enabled
```
Apply the istio manifest:
```
kubectl apply -f k8s/istio/
```

##### Find out Istio gateway

```
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
export INGRESS_HOST=$(minikube ip)
echo "Istio gateway ingress port:"
echo "http://$INGRESS_HOST:$INGRESS_PORT"
```

## 1.3. Portforwards

```
#Get the Prometheus server URL by running these commands in the same shell:
export POD_NAME=$(kubectl get pods --namespace default -l "app=prometheus,component=server" -o jsonpath="{.items[0].metadata.name}")
#Portforward a Prometheus server:
kubectl --namespace default port-forward $POD_NAME 9090 &

#Portforward for Database
kubectl port-forward service/pgdb 5555:5432 &

# Portforward for Grafana:
kubectl port-forward service/grafana 3000:3000 &

# Portforward for RabbitMQ
kubectl port-forward service/rabbitmq 5672:5672 &
```

#Portforward for pod to check togglz-console
kubectl port-forward service/your-race 8080:8080 &


## Observability and Monitoring

### Watch cluster deployments, services,..
#### Lens 

#### Watch resources in command line:
```
watch -n 1 kubectl get pod,deployment,service,horizontalpodautoscaler,ingresses,destinationrule,virtualservice 
```
### Grafana

- Open Grafana Dashboard in http://localhost:3000/login.
- Add data source Prometheus. URL: http://prometheus-server.
- Save and test.

#### Dashboard
- Import Json file [grafana/Your-Race-Performance-Analysis%20-OnePage.json](../grafana/Your-Race-Performance-Analysis%20-OnePage.json)
- Edit a widget.
- Click on Data source Prometheus + Run queries.
- Apply and save.

Important

If a Dashboard has been saved with the uid information, the imported won't run  unless you do the following:

- Goto Configruation in the Dashboard and copy JSON Model.
- Find the uid for Prometheus in the first widget:

```
    {
      "datasource": {
        "type": "prometheus",
        "uid": "rdXH35d4z"
     },
```       

 - Update the rest of prometheus uid in the script.  
 - Click save.  


## 3. Test

### 3.1. Populate Database with testing data
```
psql postgresql://admin:admin@localhost:5555/racedb -f db/export_test_data_20221118/export_202211181943.sql
```

## 3.2. Warm-up

Update your Artillery script with the Istio gateway for a just started cluster.
```
artillery run performance/raquetelio/warm-up-artilleryRaceRegistration.yml 
```

## 3.3. Performance test

### Set Feature Flag 

- Use RabbitMQ producer	
- usecb	

### Run test
Update the number of test for documenting the results in the output file:
```
artillery run performance/raquetelio/artilleryRaceRegistration.yml > performance/raquetelio/results/artilleryRaceRegistration_result_TestXX_$(date +"%Y-%m-%d-%H-%M-%s".txt)

```

## 4. Check results.

### Registrations in database
Execute script:
```
sh db/database_test_queries.bash
```
Remember to delete the registrations and set the race status to open before executing a new test.

```
sh db/database_test_delete_data.bash
```
### Grafana Dashboard
### Pod log 