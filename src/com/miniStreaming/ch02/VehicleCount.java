package com.miniStreaming.ch02;

import com.miniStreaming.ch02.job.Job;
import com.miniStreaming.ch02.job.Operation;
import com.miniStreaming.ch02.job.Source;
import com.miniStreaming.ch02.job.Stream;

public class VehicleCount {
  public static void main(String[] args) {
    Job job = new Job("ch02");

    Stream bridgeStream = job.addSource("bridge", new Source<Integer>() {
      @Override
      public Integer[] readEvents() {
        Integer out[] = new Integer[1];
        out[0] = 0;

        return out;
      }
    });

    bridgeStream.applyOperation("count", new Operation<Integer, String>() {
      @Override
      public String[] apply(Integer event) {
        String out[] = new String[1];
        out[0] = event.toString();

        return out;
      }
    });

    job.run();
  }
}
