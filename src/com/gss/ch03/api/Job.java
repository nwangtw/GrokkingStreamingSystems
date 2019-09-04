package com.gss.ch03.api;

import com.gss.ch03.engine.JobRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * The Job class is used by users to set up their jobs and run.
 */
public class Job {
  private final String name;
  private final List<Source> sourceList = new ArrayList<>();

  public Job(String jobName) {
    this.name = jobName;
  }

  /**
   * Add a source into the job. A stream is returned which will be used to connect to
   * other operators.
   * @param source The source object to be added into the job
   * @return A stream that can be used to connect to other operators
   */
  public <T> Stream<T> addSource(Source<T> source) {
    sourceList.add(source);
    return source.getOutgoingStream();
  }

  /**
   * Get the list sources in this job.
   * @return The list sources in this job
   */
  public List<Source> getSourceList() {
    return sourceList;
  }

  /**
   * Start the job.
   */
  public void run() {
    JobRunner runner = new JobRunner(this);

    runner.start();
  }
}
