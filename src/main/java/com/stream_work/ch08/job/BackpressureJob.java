package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Job;
import com.stream_work.ch08.api.Stream;
import com.stream_work.ch08.engine.JobStarter;

public class BackpressureJob {

  public static void main(String[] args) {
    Job job = new Job("credit_card_fraud_job");

    // Create a stream from a source.
    Stream bridgeStream = job.addSource(new TransactionSource("transaction source", 1, 9990));
    // One stream can be applied to more than one operators.

    // The operators will receive exactly the same data and run independently to each other.
    Stream averageTicketStream = bridgeStream.applyOperator(new AverageTicketAnalyzer("average ticket analyzer", 1));
    Stream proximityStream =  bridgeStream.applyOperator(new WindowedProximityAnalyzer("windowed proximity analyzer", 1));
    Stream transactionCountAnalyzer = bridgeStream.applyOperator(new WindowedTransactionCountAnalyzer("windowed txn count analyzer", 1));

    // This last operator will receive events from all of the previous operators
    DataStoreWriter dataStoreWriter = new DataStoreWriter("data store writer", 1);

    averageTicketStream.applyOperator(dataStoreWriter);
    proximityStream.applyOperator(dataStoreWriter);
    transactionCountAnalyzer.applyOperator(dataStoreWriter);

    Logger.log("This is a streaming job that has uses fraud detection to help you understand backpressure" +
     "Enter in dollar amounts such as '45', '20.00', or '150.25' to simulate credit card transactions" +
      "and view the output");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
