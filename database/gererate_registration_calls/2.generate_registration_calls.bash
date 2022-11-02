#!/bin/bash

BASE_URL="http://localhost:8080"
ENDPOINT='/api/tracks/byorder'
CALL_TEXT='curl -X POST '$BASE_URL$ENDPOINT' -H "content-type: application/json" -d '

docker exec k8s_pgdb_1 psql -d racedb admin -t -A -F"," -c "select '$CALL_TEXT''{ \"applicationCode\":  \"' || application_code || '\"}''' from application a" > 3.massive_registration_calls.bash

echo "done"