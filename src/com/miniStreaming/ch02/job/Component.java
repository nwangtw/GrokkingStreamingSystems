package com.miniStreaming.ch02.job;

import com.miniStreaming.ch02.job.Stream;

public class Component {
  protected Stream outgoingStream;

  public Component() {
    outgoingStream = new com.miniStreaming.ch02.job.Stream();
  }

  public com.miniStreaming.ch02.job.Stream getOutgoingStream() {
    return outgoingStream;
  }
}
