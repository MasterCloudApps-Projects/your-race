#!/bin/bash

export BASE_URL = 'http://localhost:8080'
export ENDPOINT = '/api/tracks/byorder'
export CALL_TEXT = echo 'curl -X POST '$BASE_URL$ENDPOINT'-H ''content-type: application/json''  -d '


docker exec k8s_pgdb_1 psql -d racedb admin -t -A -F"," -c "select 'curl http://localhost:8080' || '/api/tracks/byorder' || ' -H ''content-type: application/json''  -d '  || '''{ ''applicationCode'':  ''' || application_code || '''}''' from application a" > your_race_registration_calls.csv
