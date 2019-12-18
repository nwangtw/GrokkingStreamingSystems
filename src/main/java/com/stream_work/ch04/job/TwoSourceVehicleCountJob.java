package com.stream_work.ch04.job;

import com.stream_work.ch04.api.FieldsGrouping;
import com.stream_work.ch04.api.Job;
import com.stream_work.ch04.api.Stream;
import com.stream_work.ch04.engine.JobStarter;

public class TwoSourceVehicleCountJob {

  public static void main(String[] args) {
    Job job = new Job("parallelized_vehicle_count");

    // One source and two operators
    Stream bridgeStream1 = job.addSource(new Bridge("bridge", 1, 9990));
    Stream bridgeStream2 = job.addSource(new Bridge("bridge", 1, 9991));
    bridgeStream1.applyOperator(new TollBooth("booth1", 2, new FieldsGrouping()));

    System.out.println("This is a streaming job that has two counting operators linked to" +
            "the same input streams. Please enter vehicle types like 'car' and 'truck' in any of the " +
            "input terminals and look at the output");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
