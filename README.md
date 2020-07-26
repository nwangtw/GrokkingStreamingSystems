# miniStreaming
For a book

### Build the project
```
$ mvn clean package
```
After a successful build of the project you should be able to run all of the examples in the jar from the command line.

### Run the job in chapter 2
```
$ java -cp target/miniStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar com.stream_work.ch02.job.VehicleCountJob
```

### Run the job in chapter 3
```
$ java -cp target/miniStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar com.stream_work.ch03.job.ParallelizedVehicleCountJob
```
