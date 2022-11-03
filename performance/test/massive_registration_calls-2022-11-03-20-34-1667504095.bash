
SECONDS=0 
 curl -X POST http://localhost:8080/api/tracks/byorder -H "content-type: application/json" -d '{ "applicationCode": "9e7aaf749"}' curl -X POST http://localhost:8080/api/tracks/byorder -H "content-type: application/json" -d '{ "applicationCode": "2241a3518"}' 
echo "Duration: $SECONDS seconds"
