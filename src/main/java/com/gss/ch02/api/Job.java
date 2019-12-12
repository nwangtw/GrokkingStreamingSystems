package com.gss.ch02.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Job class is used by users to set up their jobs and run.
 */
public class Job {
  private final String name;
  private final List<Source> sourceList = new ArrayList<Source>();
  
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
    sourceList.add(source);
    return source.getOutgoingStream();
  }

  /**
   * Get the list sources in this job. This function is used by JobRunner to traverse the graph.
   * @return The list of sources in this job
   */
  public List<Source> getSourceList() {
    return sourceList;
  }
}
