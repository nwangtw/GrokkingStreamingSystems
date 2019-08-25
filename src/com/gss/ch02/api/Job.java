package com.gss.ch02.api;

import com.gss.ch02.runner.JobRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * The Job class is used by users to set up their jobs and run.
 */
public class Job {
  private final String name;
  private final Map<String, Source> sourceMap = new HashMap<String, Source>();

  public Job(String jobName) {
    this.name = jobName;
  }

  /**
   * Add a source into the job. A stream is returned which will be used to connect to
   * other operators.
   * @param sourceName The unique name of the source component in this job
   * @param source The source object to be added into the job
   * @return A stream that can be used to connect to other operators
   */
  public <T> Stream<T> addSource(String sourceName, Source<T> source) {
    if (sourceMap.containsKey(sourceName)) {
      throw new RuntimeException("Source " + sourceName + " already exists!");
    }
    sourceMap.put(sourceName, source);
    return source.getOutgoingStream();
  }

  /**
   * Get the map of name -> sources stored in this job.
   * @return The map of name -> sources
   */
  public Map<String, Source> getSourceMap() {
    return sourceMap;
  }

  /**
   * Start the job.
   */
  public void run() {
    JobRunner runner = new JobRunner(this);

    runner.start();
  }
}
