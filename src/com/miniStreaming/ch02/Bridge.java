package com.miniStreaming.ch02;

import com.miniStreaming.ch02.job.Source;

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
