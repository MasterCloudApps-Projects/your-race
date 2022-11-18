# Performance testing insights - Wave 1

## Insights from Exploratory Testing (Tests1 - Test49)

Full detail of results and parameters:
https://docs.google.com/spreadsheets/d/1K2KCRoR6Kmkq3UN-WFXWZY6JZzejWN9V1HZ-6EVJA_Q/edit#gid=1368903262

### Graphic
![images/your-race-graphics-wave1-test-1-49.drawio.png](./images/your-race-graphics-wave1-test-1-49.drawio.jpg)

### Contributors

For a Minikube cluster with 4 CPUs and 8GB of Memory, this parameters have been investigated and have an impact on the results improvement:

- Load of endpoint calls (with Artillery).
    - Timeout.
    - Duration	
    - ArrivalRate	
    - Max Users. 
   

#### Optimal values (by the moment)

- Load of endpoint calls (with Artillery).
    - Timeout. 80.
    - Duration. 50. But most tests are done with a value of 30.	
    - ArrivalRate. 500. 	
    - Max Users. 40.000 (This is the real use case for the 101 KMs of Ronda).


#### Best test

Test 30. 

- Duration: 300. (This is not acceptable for the use case, but worthit to find the limit value).

- 100% of race capacity registered. 
- Error Timeouts: 0%.
- Ratio of error 400: 73%.
- Ratio of error 500: 0,52%.
- Ratio of error 503: 0%.
- Ratio of error 504: 0%.


Results: [tests/artilleryRaceRegistration_result_Test30_2022-11-14-23-06-1668463611.txt](tests/artilleryRaceRegistration_result_Test30_2022-11-14-23-06-1668463611.txt).
Dashboard: ![images/artilleryRaceRegistration_result_Test30_2022-11-14-23-06-1668463611.png](images/artilleryRaceRegistration_result_Test30_2022-11-14-23-06-1668463611.png)


Question: Why I didn't get any exception about Full capacity of race reached?

### Neutrals

Parameters that don't bring a big improvement on the tests:    
- Parameter SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE. 
- Mongo DB instead of Postgres.

- Pod CPU/Memory request and limit. Why?
- Horizontal Pod Autoescaler CPU Avarage Utilization. Why?

### Errors not solved

- Timeouts. Increasing timeout limit in Artillery helps to reduce the number of timeouts. Nevertheless, we still get timeouts with testing based in other parameters. 

- 400 errors:
    - Seems like each registration call is tried to do several times, and that's why we get a lot of "AthleteAlreadyRegisteredException".
- 500 errors:
    - 503
    - 504   