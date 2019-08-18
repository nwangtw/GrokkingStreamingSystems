package com.miniStreaming.ch02;

import com.miniStreaming.ch02.job.Operation;

import java.util.HashMap;
import java.util.Map;

class Booth extends Operation<String, String> {
  private final Map<String, Integer> countMap;

  public Booth() {
    countMap = new HashMap<String, Integer>();
  }

  @Override
  public String[] apply(String vehicle) {
    Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    countMap.put(vehicle, count);
    System.out.println("vehicle: " + vehicle + ", count: " + count);

    return null;  // No output stream
  }
}
