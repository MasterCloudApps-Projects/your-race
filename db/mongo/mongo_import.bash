
#!/bin/bash

db=$1
ip=$INGRESS_HOST
path="db/mongo/export/"

cd db/mongo/export
for file in *.json;
 do c=${file#*exp_$db_}; 
    c=${c%.json}; 
    mongoimport --uri mongodb://root:password@$ip:27017/$db --collection "${c}" --file "${file}";
done