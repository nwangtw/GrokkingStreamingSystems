package com.gss.ch03.job;

import com.gss.ch03.api.Job;
import com.gss.ch03.api.Stream;

public class ParallelizedVehicleCountJob1 {

  public static void main(String[] args) {
    Job job = new Job("parallelized_vehicle_count");

    Stream bridgeStream = job.addSource(new Bridge("bridge", 2, 9990));
    bridgeStream.applyOperator(new TollBooth("booth", 1));

    System.out.println("This is a streaming job that counts vehicles from two input streams " +
            "in real time. Please enter vehicle types like 'car' and 'truck' in any of the " +
            "input terminals and look at the output");
    job.run();
  }
}
