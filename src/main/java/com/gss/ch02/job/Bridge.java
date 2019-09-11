package com.gss.ch02.job;

import com.gss.ch02.api.Source;

import java.util.List;
import java.util.Scanner;

class Bridge extends Source<String> {
  private final Scanner in = new Scanner(System.in);

  public Bridge(String name) {  super(name);  }

  @Override
  public void getEvents(List<String> eventCollector) {
    String input = in.nextLine();
    eventCollector.add(input);
  }
}
