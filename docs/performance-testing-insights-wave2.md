# Performance testing insights - Wave 2

- CronJobs don't help too much: 
    - They create a pod in the scheduled time.
    - You need to provide the pod template (duplicate the definition).
    - You need to create a CronJob per each pod you want to schedule. 
    - Results provided by incrementing replicas in deployment provide much better results.

- One you have a cluster with too much errors CON, the cluster never recover to 100% of efficiency -> you should delete it and create a new one.

We have restarted some containers in the cluster without any success.

errors.ECONNREFUSED: ........................................................... 66
errors.ECONNRESET: ............................................................. 20788
errors.ETIMEDOUT: .............................................................. 12


- The efficency of the test goes to optimal after having run 3 times a test from the creation of the cluster. Shall we try to make the tests with more time in between?

- Warming up the cluster with previous calls to the API don't improve the results. If the load is too high, they even make the cluster behave unestable and provide conectivity errors: errors.ECONREFUSED, errors.ECONNRESET AND errors.ETIMEDOUT.