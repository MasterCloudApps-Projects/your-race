
SECONDS=0 
 curl -X POST http://localhost:8080/api/tracks/byorder -H "content-type: application/json" -d '{ "applicationCode": "6a7d93147"}' curl -X POST http://localhost:8080/api/tracks/byorder -H "content-type: application/json" -d '{ "applicationCode": "2de2f5e40"}' 
echo "Duration: $SECONDS seconds"
