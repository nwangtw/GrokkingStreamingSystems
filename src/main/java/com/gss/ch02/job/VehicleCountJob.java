package com.gss.ch02.job;

import com.gss.ch02.api.Job;
import com.gss.ch02.api.Stream;

public class VehicleCountJob {

  public static void main(String[] args) {
    Job job = new Job("vehicle_count");

    Stream bridgeStream = job.addSource(new SensorReader("bridge", 9990));
    bridgeStream.applyOperator(new VehicleCounter("booth"));

    System.out.println("This is a streaming job that counts vehicles in real time. " +
        "Please enter vehicle types like 'car' and 'truck' in the input terminal " +
        "and look at the output");
    job.run();
  }
}
