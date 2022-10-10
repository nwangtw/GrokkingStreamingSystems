package com.streamwork.ch04.extra;

import com.streamwork.ch04.api.Job;
import com.streamwork.ch04.api.Stream;
import com.streamwork.ch04.engine.JobStarter;

public class StreamForkJob {

  public static void main(String[] args) {
    Job job = new Job("stream_fork_job");

    // Create a stream from a source.
    Stream bridgeStream = job.addSource(new Bridge("bridge", 1, 9990, false));
    // One stream can be applied to more than one operator.
    // The operators will receive exaclty the same data and run independently to each other.
    bridgeStream.applyOperator(new TollBooth("booth/shuffle grouping", 2));
    bridgeStream.applyOperator(new TollBooth("booth clone/fields grouping", 2, new VehicleTypeFieldsGrouping()));

    Logger.log("This is a streaming job that has two counting operators linked to " +
               "the same input stream. One operator is configured with default " +
               "grouping strategy (shuffle) and the other is configured with fields " +
               "grouping strategy. Please enter vehicle types like 'car' and 'truck' in the "  +
               "input terminal and look at the output");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
