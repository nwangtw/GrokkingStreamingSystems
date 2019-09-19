package com.gss.ch03.job;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.Source;

import java.util.List;
import java.util.Random;

class Bridge extends Source {
  private final String [] vehicles = new String[] {
      "car", "truck", "van"
  };
  static private final Random rand = new Random();

  public Bridge(String name, int parallelism) {
    super(name, parallelism);
  }

  @Override
  public void getEvents(int instance, List<Event> eventCollector) {
    try {
      Thread.sleep(2000);  // One vehicle every 2 seconds
    } catch (InterruptedException e) {
      // Keep working.
    }
    String vehicle = vehicles[rand.nextInt(vehicles.length)];
    eventCollector.add(new VehicleEvent(vehicle));
    System.out.println("bridge instance " + instance + " --> " + vehicle);
  }
}
