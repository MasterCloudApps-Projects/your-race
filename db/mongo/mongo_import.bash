
#!/bin/bash

db=$1
out_dir=$2

for file in export/*.json;
 do c=${file#*exp_$db_}; 
    c=${c%.json}; 
    mongoimport --db $db --collection "${c}" --file "${file}";
 done