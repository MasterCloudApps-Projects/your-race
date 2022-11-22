#!bin/bash

# Provide test number
TEST_NUM="$1"

SECONDS=0

echo "Running test..."
artillery run performance/raquetelio/artilleryRaceRegistration.yml > performance/raquetelio/results/artilleryRaceRegistration_result_Test\_$TEST_NUM\_$(date +"%Y-%m-%d-%H-%M-%s".txt)
echo "Database Results..."
bash db/database_test_queries.bash
echo "Test End. Duration: $SECONDS seconds ("$(( SECONDS / 60 )) "minutes)("$(( SECONDS / 3060 )) "hours)"

echo "Registrations on DB could be on the fly. Execute this command to get the final count of registrations:"
echo "bash db/database_test_queries.bash"
