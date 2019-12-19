To execute this job:

In terminal 1:
nc -lk 9990

In terminal 2:
nc -lk 9991

In terminal 3:
java -cp target/miniStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar com.stream_work.ch04.job.StreamForkJob
or
java -cp target/miniStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar com.stream_work.ch04.job.StreamMergeJob
or
java -cp target/miniStreaming-1.0-SNAPSHOT-jar-with-dependencies.jar com.stream_work.ch04.job.StreamSplitJob