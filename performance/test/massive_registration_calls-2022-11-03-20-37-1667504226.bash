
SECONDS=0 
 curl -X POST http://localhost:8080/api/tracks/byorder -H "content-type: application/json" -d '{ "applicationCode": "94791dbfc"}' curl -X POST http://localhost:8080/api/tracks/byorder -H "content-type: application/json" -d '{ "applicationCode": "2faa1e590"}' 
echo "Duration: $SECONDS seconds"
