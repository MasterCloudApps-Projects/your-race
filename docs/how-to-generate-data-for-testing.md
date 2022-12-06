
# How to generate data for Testing

## Massive registration calls in One script
(This is an Alternative to running an Artillery script)

Run script [generate_and_send_registration_calls.bash](../db/gererate_registration_calls/generate_and_send_registration_calls.bash) to send massive calls to register athletes to a race:

```
bash db/gererate_registration_calls/generate_and_send_registration_calls.bash
```

You can set the number of applicant athletes and the race capacity by editing the script [1.prepare_basic_data.psql](/db/gererate_registration_calls/1.prepare_basic_data.psql).



## Massive registration calls Step by step

### [Optional] Remove generated test data if you have previously run the scripts
```
docker cp db/gererate_registration_calls/_delete_basic_data.sql k8s_pgdb_1:/var/lib/postgresql/_delete_basic_data.sql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/_delete_basic_data.sql 
```

### 1. Prepare basic data for race in database.

This script creates an Organizer, a Race (with ApplicationPeriod, RegistrationRace and the capacity set in the parameter 'athlete_capacity') and as many Ahtletes and Applications to the race as set in parameter 'athlete_applications'.

```
docker cp db/gererate_registration_calls/1.prepare_basic_data.psql k8s_pgdb_1:/var/lib/postgresql/1.prepare_basic_data.psql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/1.prepare_basic_data.psql 
``` 

### 2. Generate the registration calls.

This script generates a file with the calls to the endpoint for register the Athletes in the Race (ByOrder) with their applicationCodes. 
```
REGISTRATION_CALLS_FILE_NAME="performance/test/massive_registration_calls-"$(date +"%Y-%m-%d-%H-%M-%s".bash)
bash db/gererate_registration_calls/2.generate_registration_calls.bash $REGISTRATION_CALLS_FILE_NAME
```

### 3. Run the resulting script with the registration calls.
(This is an Alternative to running an Artillery script)

Run the script and track the time spent in the process of the file through 'SECONDS' shell variable. Results are saved in the '*result.txt' file.


```
SECONDS=0
bash $REGISTRATION_CALLS_FILE_NAME
echo "Duration: $SECONDS seconds ("$(( SECONDS / 60 )) "minutes)("$(( SECONDS / 3060 )) "hours)" > ${REGISTRATION_CALLS_FILE_NAME}_result.txt
echo `cat ${REGISTRATION_CALLS_FILE_NAME}_result.txt`
```

## How to populate some data for local exploratory testing

Copy initializer script to docker container and run script over it:
```
docker cp db/smoke_testing_data/populate_initial_data.sql k8s_pgdb_1:/var/lib/postgresql 
docker exec k8s_pgdb_1 psql racedb admin -f /var/lib/postgresql/populate_initial_data.sql
```

Check out generated ids in file [database_initial_ids.txt](/db/smoke_testing_data/database_initial_ids.txt).