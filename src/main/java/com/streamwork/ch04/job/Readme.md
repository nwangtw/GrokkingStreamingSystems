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
You have a few options to choose from.
The following will execute a job with a forked stream.
```bash
java -cp ./target/gss.jar com.streamwork.ch04.job.StreamForkJob
```
The following will execute a job with a merged stream.
```bash
java -cp ./target/gss.jar com.streamwork.ch04.job.StreamMergeJob
```
The following will execute a job with a split stream.

```bash
java -cp ./target/gss.jar com.streamwork.ch04.job.StreamSplitJob
```
The following will execute a fraud detection job.

```bash
java -cp ./target/gss.jar com.streamwork.ch04.job.FraudDetectionJob
```
