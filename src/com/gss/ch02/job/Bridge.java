package com.gss.ch02.job;

import com.gss.ch02.api.Source;

import java.util.Scanner;

class Bridge extends Source<String> {
  private Scanner in = new Scanner(System.in);

  @Override
  public String[] getEvents() {
    String out[] = new String[1];

    String input = in.nextLine();
    out[0] = input;

    return out;
  }
}
