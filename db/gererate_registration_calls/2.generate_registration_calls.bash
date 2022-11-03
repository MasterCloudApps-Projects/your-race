#!/bin/bash

echo "starting..."
BASE_URL="http://localhost:8080"
ENDPOINT='/api/tracks/byorder'
CALL_TEXT='curl -X POST '$BASE_URL$ENDPOINT' -H "content-type: application/json" -d '
OUTPUT_FILE="$1"

docker exec k8s_pgdb_1 psql -d racedb admin -t -A -F"," -c "select '$CALL_TEXT''{ \"applicationCode\":  \"' || application_code || '\"}''' from application a" > $OUTPUT_FILE

echo "done"