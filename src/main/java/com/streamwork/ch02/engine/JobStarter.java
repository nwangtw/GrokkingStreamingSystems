package com.streamwork.ch02.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.streamwork.ch02.api.Component;
import com.streamwork.ch02.api.Job;
import com.streamwork.ch02.api.Operator;
import com.streamwork.ch02.api.Source;
import com.streamwork.ch02.api.Stream;

public class JobStarter {
  private final static int QUEUE_SIZE = 64;

  // The job to start
  private final Job job;
  // List of executors
  private final List<ComponentExecutor> executorList = new ArrayList<ComponentExecutor>();

  // Connections between component executors
  private final List<Connection> connectionList = new ArrayList<Connection>();

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

    // Start web server
    new WebServer(job.getName(), connectionList).start();
  }

  /**
   * Create all source and operator executors.
   */
  private void setupComponentExecutors() {
    // Start from sources in the job and traverse components to create executors
    for (Source source: job.getSources()) {
      SourceExecutor executor = new SourceExecutor(source);
      executorList.add(executor);
      // For each source, traverse the operations connected to it.
      traverseComponent(source, executor);
    }
  }

  /**
   * Set up connections (intermediate queues) between all component executors.
   */
  private void setupConnections() {
    for (Connection connection: connectionList) {
      connectExecutors(connection);
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

  private void connectExecutors(Connection connection) {
    // It is a newly connected operator executor. Note that in this version, there is no
    // shared "from" component and "to" component. The job looks like a single linked list.
    EventQueue intermediateQueue = new EventQueue(QUEUE_SIZE);
    connection.from.setOutgoingQueue(intermediateQueue);
    connection.to.setIncomingQueue(intermediateQueue);
  }

  private void traverseComponent(Component component, ComponentExecutor executor) {
    Stream stream = component.getOutgoingStream();

    for (Operator operator: stream.getAppliedOperators()) {
      OperatorExecutor operatorExecutor = new OperatorExecutor(operator);
      executorList.add(operatorExecutor);
      connectionList.add(new Connection(executor, operatorExecutor));
      // Setup executors for the downstream operators.
      traverseComponent(operator, operatorExecutor);
    }
  }
}
