package com.miniStreaming.ch02.job;

import com.miniStreaming.ch02.job.Source;
import com.miniStreaming.ch02.job.Stream;
import com.miniStreaming.ch02.runner.JobRunner;

import java.util.HashMap;
import java.util.Map;

public class Job {
  private final String name;
  private final Map<String, Source> sourceMap;

  public Job(String jobName) {
    this.name = jobName;
    this.sourceMap = new HashMap<String, com.miniStreaming.ch02.job.Source>();
  }

  public Stream addSource(String sourceName, com.miniStreaming.ch02.job.Source source) {
    if (sourceMap.containsKey(sourceName)) {
      throw new RuntimeException("Source " + sourceName + " already exists!");
    }
    sourceMap.put(sourceName, source);
    return source.getOutgoingStream();
  }

  public Map<String, com.miniStreaming.ch02.job.Source> getSourceMap() {
    return sourceMap;
  }

  public void run() {
    JobRunner runner = new JobRunner(this);

    runner.start();
  }
}
