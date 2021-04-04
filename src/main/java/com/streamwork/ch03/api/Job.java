package com.streamwork.ch03.api;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Job class is used by users to set up their jobs and run.
 */
public class Job {
  private final String name;
  private final Set<Source> sourceSet = new HashSet<Source>();

  public Job(String jobName) {
    this.name = jobName;
  }

  /**
   * Add a source into the job. A stream is returned which will be used to connect to
   * other operators.
   * @param source The source object to be added into the job
   * @return A stream that can be used to connect to other operators
   */
  public Stream addSource(Source source) {
    if (sourceSet.contains(source)) {
      throw new RuntimeException("Source " + source.getName() + " is added to job twice");
    }

    sourceSet.add(source);
    return source.getOutgoingStream();
  }

  /**
   * Get the name of this job.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the list sources in this job. This function is used by JobRunner to traverse the graph.
   * @return The list of sources in this job
   */
  public Collection<Source> getSources() {
    return sourceSet;
  }
}
