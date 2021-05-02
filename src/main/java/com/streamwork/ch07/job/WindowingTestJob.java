package com.streamwork.ch07.job;

import com.streamwork.ch07.api.FixedWindowingStrategy;
import com.streamwork.ch07.api.Job;
import com.streamwork.ch07.engine.JobStarter;

public class WindowingTestJob {

  public static void main(String[] args) {
    Job job = new Job("windowing_test_job");

    // Create a stream from a source.
    job.addSource(new TransactionSource("transaction source", 1, 9990))
        // Apply a windowing strategy and then apply a windowed operator.
        .applyOperator(new TestWindowedAnalyzer("test windowed analyzer", 2,
            new UserAccountFieldsGrouping(), new FixedWindowingStrategy()));

    Logger.log("This is a streaming job that works with a windowed strategy and a windowed operator." +
               "Input needs to be in this format: {amount},{merchandiseId}. For example: 42.00@3.");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
