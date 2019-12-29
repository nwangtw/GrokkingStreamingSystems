package com.gss.ch03.job;

import com.gss.ch03.api.Job;
import com.gss.ch03.api.Stream;

public class ParallelizedVehicleCountJob2 {

  public static void main(String[] args) {
    Job job = new Job("parallelized_vehicle_count");

    Stream bridgeStream = job.addSource(new SensorReader("sensor-reader", 1, 9990));
    // Shuffle grouping is the default grouping strategy.
    bridgeStream.applyOperator(new VehicleCounter("vehicle-counter", 2));

    System.out.println("This is a streaming job that counts vehicles from the input stream " +
            "in real time. Please enter vehicle types like 'car' and 'truck' in the " +
            "input terminal and look at the output");
    job.run();
  }
}
