To execute this job:

In terminal 1:
```bash
nc -lk 9990
```

In terminal 2:
```bash
nc -lk 9991
```

In terminal 3:
You have a few options to choose from.
The following will execute a job with 2 source instances and 1 operator instance.
```bash
java -cp ./target/miniStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar \
com.stream_work.ch03.job.ParallelizedVehicleCountJob1
```
The following will execute a job with 1 source instance and 2 operator instances with a shuffle grouping
```bash
java -cp ./target/miniStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar \
com.stream_work.ch03.job.ParallelizedVehicleCountJob2
```
The following will execute a job with 2 source instances and 2 operator instance with a fields grouping

```bash
java -cp ./target/miniStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar \
com.stream_work.ch03.job.ParallelizedVehicleCountJob3
```