package com.gss.ch02.job;

import com.gss.ch02.api.Job;
import com.gss.ch02.api.Stream;

public class VehicleCountJob {

  public static void main(String[] args) {
    Job job = new Job("vehicle_count");

    Stream bridgeStream = job.addSource(new Bridge("bridge"));
    bridgeStream.applyOperator(new Booth("booth"));

    System.out.println("This is a streaming job that counts vehicles in real time. " +
        "Please enter vehicle types like 'car' and 'truck' and look at the output");
    job.run();
  }
}
