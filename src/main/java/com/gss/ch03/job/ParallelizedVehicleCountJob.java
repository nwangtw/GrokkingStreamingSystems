package com.gss.ch03.job;

import com.gss.ch03.api.Job;
import com.gss.ch03.api.Stream;

public class ParallelizedVehicleCountJob {

  public static void main(String[] args) {
    Job job = new Job("parallelized_vehicle_count");

    Stream bridgeStream = job.addSource(new Bridge("bridge", 3));
    bridgeStream.applyOperator(new TollBooth("booth", 2, new FieldsGrouping()));

    job.run();
  }
}
