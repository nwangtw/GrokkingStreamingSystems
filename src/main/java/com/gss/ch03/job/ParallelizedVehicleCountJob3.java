package com.gss.ch03.job;

import com.gss.ch03.api.FieldsGrouping;
import com.gss.ch03.api.Job;
import com.gss.ch03.api.Stream;

public class ParallelizedVehicleCountJob3 {

  public static void main(String[] args) {
    Job job = new Job("parallelized_vehicle_count");

    Stream bridgeStream = job.addSource(new Bridge("bridge", 1, 9990));
    bridgeStream.applyOperator(new TollBooth("booth", 2, new FieldsGrouping()));

    System.out.println("This is a streaming job that counts vehicles from the input stream " +
            "in real time. Please enter vehicle types like 'car' and 'truck' in the " +
            "input terminal and look at the output");
    job.run();
  }
}
