
#!/bin/bash

#For database in cluster, make sure port is forwarded

echo "Deleting registrations:"
psql postgresql://admin:admin@localhost:5555/racedb -c "delete from track"

echo "Deleting registrations:"
psql postgresql://admin:admin@localhost:5555/racedb -c "update race set status=2 where id=10003"


echo "Done"
