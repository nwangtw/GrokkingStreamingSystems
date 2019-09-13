package com.gss.ch03.job;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.Source;

import java.util.List;
import java.util.Scanner;

class Bridge extends Source {
  private transient Scanner in;

  public Bridge(String name, int parallelism) {
    super(name, parallelism);
  }

  @Override
  public void setup() {
    in = new Scanner(System.in);
  }

  @Override
  public void getEvents(List<Event> eventCollector) {
    String input = in.nextLine();
    eventCollector.add(new VehicleEvent(input));
  }
}
