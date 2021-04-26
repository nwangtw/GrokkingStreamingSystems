package com.streamwork.ch05.job;

import com.streamwork.ch05.api.Job;
import com.streamwork.ch05.engine.JobStarter;

public class SystemUsageJob {

  public static void main(String[] args) {
    Job job = new Job("system_usage_job");

    // Create a stream from a source.
    job.addSource(new TransactionSource("transaction source", 1, 9990))
      .applyOperator(new SystemUsageAnalyzer("system usage analyzer", 2, new TransactionFieldsGrouping()))
      .applyOperator(new UsageWriter("usage writer", 2, new TransactionFieldsGrouping()));

    Logger.log("This is a streaming job that detect suspicious transactions." +
               "Input needs to be in this format: {amount},{merchandiseId}. For example: 42.00@3." +
               "Merchandises N and N + 1 are 1 seconds walking distance away from each other.");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
