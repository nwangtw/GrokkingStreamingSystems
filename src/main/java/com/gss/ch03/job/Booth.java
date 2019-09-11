package com.gss.ch03.job;

import com.gss.ch03.api.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Booth extends Operator<String, Object> {
  private transient Map<String, Integer> countMap;

  public Booth(String name, int parallelism) {
    super(name, parallelism);
  }

  @Override
  public void setup() {
    countMap = new HashMap<String, Integer>();
  }

  @Override
  public void apply(String vehicle, List<Object> eventCollector) {
    Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    countMap.put(vehicle, count);

    System.out.println("vehicle: " + vehicle + ", count: " + count);
  }
}
