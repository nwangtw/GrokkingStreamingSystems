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
java -cp ./target/gss.jar com.streamwork.ch07.job.WindowingTestJob
```
