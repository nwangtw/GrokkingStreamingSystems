package com.gss.ch03.job;

import com.gss.ch03.api.FieldsGrouping;
import com.gss.ch03.api.Job;
import com.gss.ch03.api.Stream;

public class ParallelizedVehicleCountJob {

  public static void main(String[] args) {
    Job job = new Job("parallelized_vehicle_count");

    Stream bridgeStream = job.addSource(new SensorReader("sensor-reader", 2, 9990));
    bridgeStream.applyOperator(new VehicleCounter("vehicle-counter", 3, new FieldsGrouping()));

    System.out.println("This is a streaming job that counts vehicles from two input streams " +
            "in real time. Please enter vehicle types like 'car' and 'truck' in any of the " +
            "input terminals and look at the output");
    job.run();
  }
}
