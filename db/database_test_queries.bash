
#!/bin/bash

#For database in cluster, make sure port is forwarded

echo "Applications:"
psql postgresql://admin:admin@localhost:5555/racedb -c "select count(*) from application a"

echo "Registrations:"
psql postgresql://admin:admin@localhost:5555/racedb -c "select count(*) from track"

echo "Duplicated registrations:"
psql postgresql://admin:admin@localhost:5555/racedb -c "select count(*) from track group by athlete_id , race_id having count(*)>1"

echo "Execute this script to delete registrations and reset race status to open:"
echo "sh db/database_test_delete_data.bash"
