package com.streamwork.ch04.extra;

import com.streamwork.ch04.api.Job;
import com.streamwork.ch04.api.Stream;
import com.streamwork.ch04.engine.JobStarter;

public class StreamSplitJob {

  public static void main(String[] args) {
    Job job = new Job("stream_split_job");

    // Create a stream from a source.
    Stream bridgeStream = job.addSource(new Bridge("bridge", 1, 9990, true));
    // One stream can have multiple channels. Different operator can be hooked up
    // to different channels to receive different events. When no channel is selected,
    // the default channel will be used.
    bridgeStream.applyOperator(new TollBooth("booth/shuffle grouping", 2));
    bridgeStream.selectChannel("clone")
                .applyOperator(new TollBooth("booth clone/fields grouping", 2, new VehicleTypeFieldsGrouping()));

    Logger.log("This is a streaming job that has two counting operators linked to " +
                "the same input stream. One operator is hooked up to the default channel of " +
                "the stream and configured with default grouping strategy (shuffle). " +
                "The other one is hooked up to the clone channel and configured with " +
                "fields grouping strategy. Please enter vehicle types like 'car' and " +
                "'truck' in the input terminal and look at the output");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
