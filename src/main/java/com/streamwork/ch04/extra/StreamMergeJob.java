package com.streamwork.ch04.extra;

import com.streamwork.ch04.api.Job;
import com.streamwork.ch04.api.Stream;
import com.streamwork.ch04.api.Streams;
import com.streamwork.ch04.engine.JobStarter;

public class StreamMergeJob {

  public static void main(String[] args) {
    Job job = new Job("stream_merge_job");

    // Create two streams from a sources.
    Stream bridgeStream1 = job.addSource(new Bridge("bridge1", 1, 9990, false));
    Stream bridgeStream2 = job.addSource(new Bridge("bridge2", 1, 9991, true));
    // This is the convenient way to apply an operator on multiple streams using
    // Streams.merge(...) function. The code works the same way as the following:
    //   Operator operator = new TollBooth("booth", 2, new FieldsGrouping());
    //   bridgeStream1.selectChannel("clone").applyOperator(operator);
    //   bridgeStream2.applyOperator(operator);

    Streams.of(bridgeStream1, bridgeStream2.selectChannel("clone"))
           .applyOperator(new TollBooth("booth", 2, new VehicleTypeFieldsGrouping()));

    Logger.log("This is a streaming job that has one counting operator linked to " +
           "two input streams. One operator is configured with default " +
           "grouping strategy (shuffle) and the other is configured with fields " +
           "grouping strategy. Please enter vehicle types like 'car' and 'truck' in the "  +
           "input terminal and look at the output");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
