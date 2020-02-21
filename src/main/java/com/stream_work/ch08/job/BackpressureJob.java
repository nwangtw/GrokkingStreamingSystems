package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Job;
import com.stream_work.ch08.api.Stream;
import com.stream_work.ch08.engine.JobStarter;
import com.stream_work.ch08.util.CacheManager;
import com.stream_work.ch08.util.InMemoryCache;

public class BackpressureJob {

  public static void main(String[] args) {
    Job job = new Job("backpressure_job");
    InMemoryCache inMemoryCache = new InMemoryCache();
    CacheManager cacheManager = new CacheManager(inMemoryCache);


    // Create a stream from a source.
    Stream bridgeStream = job.addSource(new TransactionSource("transaction source", 1, 9990));
    // One stream can be applied to more than one operators.

    // The operators will receive exactly the same data and run independently to each other.
    Stream averageTicketStream = bridgeStream.selectChannel("default").applyOperator(new TransactionAmountAnalyzer("average ticket analyzer", 1));
    Stream proximityStream =  bridgeStream.selectChannel("clone0").applyOperator(new TransactionLocationAnalyzer("proximity analyzer", 1));
    Stream transactionCountAnalyzer = bridgeStream.selectChannel("clone1").applyOperator(new TransactionDateAnalyzer("txn count analyzer", 1));

    // This last operator will receive events from all of the previous operators
    DataStoreWriter dataStoreWriter = new DataStoreWriter("data store writer", 1, cacheManager);
    averageTicketStream.applyOperator(dataStoreWriter);
    proximityStream.applyOperator(dataStoreWriter);
    transactionCountAnalyzer.applyOperator(dataStoreWriter);

    Logger.log("\n\n" +
     "#################################\n" +
      "This is a streaming job that has uses fraud detection to help you understand backpressure\n" +
     "Enter in dollar amounts such as '45', '20.00', or '150.25' to simulate credit card " +
      "transactions and view the output\n" +
      "#################################\n\n");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }

}
