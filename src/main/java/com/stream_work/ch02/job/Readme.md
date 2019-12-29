To execute this job:

In terminal 1:
```bash
nc -lk 9990
```
In terminal 2:
```bash
java -cp target/miniStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar com.stream_work.ch02.job.VehicleCountJob
```
