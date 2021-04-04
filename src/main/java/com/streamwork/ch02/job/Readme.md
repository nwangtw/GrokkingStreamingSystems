# Build
```bash
mvn clean package
```

# Run
In terminal 1:
```bash
nc -lk 9990
```
In terminal 2:
```bash
java -cp target/miniStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar com.streamwork.ch02.job.VehicleCountJob
```
