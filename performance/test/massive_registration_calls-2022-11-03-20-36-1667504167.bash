
SECONDS=0 
 curl -X POST http://localhost:8080/api/tracks/byorder -H "content-type: application/json" -d '{ "applicationCode": "5757c9ec5"}' curl -X POST http://localhost:8080/api/tracks/byorder -H "content-type: application/json" -d '{ "applicationCode": "77c781761"}' 
echo "Duration: $SECONDS seconds"
