
#!/bin/bash

db=$1

cd db/mongo/export
for file in *.json;
 do c=${file#*exp_$db_}; 
    c=${c%.json}; 
    mongoimport --uri mongodb://root:password@192.168.49.2:27017/$db --collection "${c}" --file "${file}";
 done

