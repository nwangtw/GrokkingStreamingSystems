package com.stream_work.ch03.job;

import com.stream_work.ch03.api.FieldsGrouping;
import com.stream_work.ch03.api.Job;
import com.stream_work.ch03.api.Stream;
import com.stream_work.ch03.engine.JobStarter;

public class ParallelizedVehicleCountJob {

  public static void main(String[] args) {
    Job job = new Job("parallelized_vehicle_count");

    Stream bridgeStream = job.addSource(new Bridge("bridge", 2, 9990));
    bridgeStream.applyOperator(new TollBooth("booth", 3, new FieldsGrouping()));

    System.out.println("This is a streaming job that counts vehicles from two input streams " +
            "in real time. Please enter vehicle types like 'car' and 'truck' in any of the " +
            "input terminals and look at the output");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
