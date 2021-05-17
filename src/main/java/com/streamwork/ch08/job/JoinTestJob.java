package com.streamwork.ch08.job;

import java.util.Map;
import java.util.stream.Collectors;

import com.streamwork.ch08.api.FixedTimeWindowingStrategy;
import com.streamwork.ch08.api.Job;
import com.streamwork.ch08.api.Stream;
import com.streamwork.ch08.engine.JobStarter;

public class JoinTestJob {

  private static final long FIXED_WINDOW_INTERVAL_MS = 5 * 1000;
  private static final long FIXED_WINDOW_WATERMARK_MS = 2 * 1000;

  public static void main(String[] args) {
    Job job = new Job("join_test_job");

    // Create a stream from a source.
    Stream transactions = job.addSource(new TransactionSource("transaction source", 1, 9990));
    Stream temperatures = job.addSource(new TemperatureSource("temperature source", 1, 9991));

    //transactions.join(new Operator(), Map.of("temperature", temperatures));
        // Apply a windowing strategy and then apply a windowed operator.
        //.applyOperator(new TestWindowedAnalyzer("test windowed analyzer", 2, new UserAccountFieldsGrouping()));

    Logger.log("This is a streaming job that works with a windowed strategy and a windowed operator." +
               "Input needs to be in this format: {amount},{merchandiseId}. For example: 42.00@3.");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
