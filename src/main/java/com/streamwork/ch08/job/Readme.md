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
nc -lk 9991
```

In terminal 3:
```bash
java -cp ./target/gss.jar com.streamwork.ch08.job.EmissionJob
```
