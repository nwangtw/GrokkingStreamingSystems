package com.stream_work.ch02.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stream_work.ch02.api.Component;
import com.stream_work.ch02.api.Job;
import com.stream_work.ch02.api.Operator;
import com.stream_work.ch02.api.Source;
import com.stream_work.ch02.api.Stream;

public class JobStarter {
  private final static int QUEUE_SIZE = 64;

  // The job to start
  private final Job job;
  // List of executors
  private final List<ComponentExecutor> executorList = new ArrayList<ComponentExecutor>();
  
  // Connections between component executors
  private final Map<ComponentExecutor, List<OperatorExecutor>> connectionMap = new HashMap<ComponentExecutor, List<OperatorExecutor>>();
  
  public JobStarter(Job job) {
    this.job = job;
  }

  public void start() {
    // Set up executors for all the components.
    setupComponentExecutors();

    // All components are created now. Build the connections to connect the components together.
    setupConnections();

    // Start all the processes.
    startProcesses();
  }

  /**
   * Create all source and operator executors.
   */
  private void setupComponentExecutors() {
    // Start from sources in the job and traverse components to create executors
    for (Source source: job.getSources()) {
      SourceExecutor executor = new SourceExecutor(source);
      executorList.add(executor);
      // For each source, traverse the the operations connected to it.
      List<OperatorExecutor> operatorExecutors = traverseComponent(source);
      connectionMap.put(executor, operatorExecutors);
    }
  }

  /**
   * Set up connections (intermediate queues) between all component executors.
   */
  private void setupConnections() {
    for (Map.Entry<ComponentExecutor, List<OperatorExecutor>> entry: connectionMap.entrySet()) {
      for (OperatorExecutor target: entry.getValue()) {
        connectExecutors(entry.getKey(), target);
      }
    }
  }

  /**
   * Start all the processes for the job.
   */
  private void startProcesses() {
    Collections.reverse(executorList);
    for (ComponentExecutor executor: executorList) {
      executor.start();
    }
  }

  private void connectExecutors(ComponentExecutor from, OperatorExecutor to) {
    // It is a newly connected operator executor. Note that in this version, there is no
    // shared "from" component and "to" component. The job looks like a single linked list.
    EventQueue intermediateQueue = new EventQueue(QUEUE_SIZE);
    from.setOutgoingQueue(intermediateQueue);
    to.setIncomingQueue(intermediateQueue);
  }

  private List<OperatorExecutor> traverseComponent(Component component) {
    Stream stream = component.getOutgoingStream();

    List<OperatorExecutor> operatorExecutors = new ArrayList<OperatorExecutor>();
    for (Operator op: stream.getOperators()) {
      OperatorExecutor executor = new OperatorExecutor(op);
      operatorExecutors.add(executor);
      executorList.add(executor);
      // Setup executors for the downstream operators.
      List<OperatorExecutor> downstreamExecutors = traverseComponent(op);
      connectionMap.put(executor, downstreamExecutors);
    }

    return operatorExecutors;
  }
}
