package com.gss.ch02_with_stmgr.job;

import com.gss.ch02.api.Job;
import com.gss.ch02.api.Stream;
import com.gss.ch02_with_stmgr.engine.JobStarter;

public class VehicleCountJob {

  public static void main(String[] args) {
    Job job = new Job("vehicle_count");

    Stream bridgeStream = job.addSource(new Bridge("bridge", 9990));
    bridgeStream.applyOperator(new TollBooth("booth"));

    System.out.println("This is a streaming job that counts vehicles in real time. " +
        "Please enter vehicle types like 'car' and 'truck' in the input terminal " +
        "and look at the output");
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
