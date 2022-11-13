
#!/bin/bash

db=$1
ip=$INGRESS_HOST
path="db/mongo/export/"

#cd db/mongo/export
#for file in *.json;
# do c=${file#*exp_$db_}; 
#    c=${c%.json}; 
#    mongoimport --uri mongodb://root:password@$ip:27017/$db --collection "${c}" --file "${file}";
# done

c="race"
file=$path"exp_racedb_race.json"
#mongoimport --authenticationDatabase= admin --uri mongodb://root:password@$ip:27017/$db --collection "${c}" --file "${file}";

mongoimport --uri=mongodb://root:password@$ip:27017/$db --collection="race" --file=$file;


c="athlete"
file="exp_racedb_athlete.json"
mongoimport --authenticationDatabase= admin --uri mongodb://root:password@$ip:27017/$db --collection "${c}" --file "${file}";

c="application"
file="exp_racedb_application.json"
mongoimport --authenticationDatabase= admin --uri mongodb://root:password@$ip:27017/$db --collection "${c}" --file "${file}";