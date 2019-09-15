package com.gss.ch03.job;

import com.gss.ch03.api.IGroupingStrategy;
import com.gss.ch03.api.Job;
import com.gss.ch03.api.Stream;

public class ParallelizedVehicleCount {

  public static void main(String[] args) {
    Job job = new Job("parallelized_vehicle_count");

    Stream bridgeStream = job.addSource(new Bridge("bridge", 3));
    IGroupingStrategy vehicleGrouping = new VehicleGrouping();
    bridgeStream.applyOperator(new TollBooth("booth", 2, vehicleGrouping));

    job.run();
  }
}
