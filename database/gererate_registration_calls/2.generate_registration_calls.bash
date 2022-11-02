#!/bin/bash

BASE_URL="http://localhost:8080"
ENDPOINT='/api/tracks/byorder'
CALL_TEXT='curl -X POST '$BASE_URL$ENDPOINT' -H "content-type: application/json" -d '

#CALL_TEXT='curl -X POST http://localhost:8080/api/tracks/byorder -H \'content-type: application/json\' -d '

docker exec k8s_pgdb_1 psql -d racedb admin -t -A -F"," -c "select '$CALL_TEXT''{ \"applicationCode\":  \"' || application_code || '\"}''' from application a" > your_race_registration_calls.csv

echo "done"