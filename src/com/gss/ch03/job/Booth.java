package com.gss.ch03.job;

import com.gss.ch03.api.Operator;

import java.util.HashMap;
import java.util.Map;

class Booth extends Operator<String, Object> {
  private final Map<String, Integer> countMap = new HashMap<String, Integer>();

  public Booth(String name, int parallelism) {  super(name, parallelism);  }

  @Override
  public String[] apply(String vehicle) {
    Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    countMap.put(vehicle, count);

    System.out.println("vehicle: " + vehicle + ", count: " + count);

    return null;  // No output data
  }
}
