package com.gss.ch03.job;

import com.gss.ch03.api.Source;

import java.util.Scanner;

class Bridge extends Source<String> {
  private Scanner in = new Scanner(System.in);

  public Bridge(String name, int parallelism) {  super(name, parallelism);  }

  @Override
  public String[] getEvents() {
    String out[] = new String[1];

    String input = in.nextLine();
    out[0] = input;

    return out;
  }
}
