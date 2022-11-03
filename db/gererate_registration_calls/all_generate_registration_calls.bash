#!/bin/bash

### Removing generated test data
docker cp db/gererate_registration_calls/_delete_basic_data.sql k8s_pgdb_1:/var/lib/postgresql/_delete_basic_data.sql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/_delete_basic_data.sql 


#1. Prepare basic data for race in database.
docker cp db/gererate_registration_calls/1.prepare_basic_data.psql k8s_pgdb_1:/var/lib/postgresql/1.prepare_basic_data.psql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/1.prepare_basic_data.psql 

#2. Run shell script providing the results file name.
REGISTRATION_CALLS_FILE_NAME="performance/test/massive_registration_calls-"$(date +"%Y-%m-%d-%H-%M-%s".bash)
bash db/gererate_registration_calls/2.generate_registration_calls.bash $REGISTRATION_CALLS_FILE_NAME

#3. Run the resulting script with the endpoint calls.
bash $REGISTRATION_CALLS_FILE_NAME
