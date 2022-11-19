
#!/bin/bash

#delete from track


#select count(*) from track

#select count(*) from track
#group by athlete_id , race_id 
#having count(*)>1


#select count(*) from application a 

echo "Applications:"
psql postgresql://admin:admin@localhost:5555/racedb -c "select count(*) from application a"

echo "Registrations:"
psql postgresql://admin:admin@localhost:5555/racedb -c "select count(*) from track"

echo "Duplicated registratios:"
psql postgresql://admin:admin@localhost:5555/racedb -c "select count(*) from track group by athlete_id , race_id having count(*)>1"

echo "Execute this command to delete registrations:"
echo "psql postgresql://admin:admin@localhost:5555/racedb -c \"delete from track\""
