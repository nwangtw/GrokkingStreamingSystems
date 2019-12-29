package com.gss.ch03.engine;

import com.gss.ch03.api.IComponent;
import com.gss.ch03.api.Job;
import com.gss.ch03.api.Operator;
import com.gss.ch03.api.Source;
import com.gss.ch03.api.Stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;;

public class JobRunner {
  private class Connection {
    private final ComponentExecutor from;
    private final ComponentExecutor to;

    public Connection(ComponentExecutor from, ComponentExecutor to) {
      this.from = from;
      this.to = to;
    }

    public ComponentExecutor getFrom() { return from; }
    public ComponentExecutor getTo() { return to; }
  }

  private final Job job;
  private final List<ComponentExecutor> executorList;
  private final Map<ComponentExecutor, List<OperatorExecutor>> connectionMap;
  private final List<StreamManager> streamManagerList;

  public JobRunner(Job job) {
    this.job = job;
    this.executorList = new ArrayList<ComponentExecutor>();
    this.connectionMap = new HashMap<ComponentExecutor, List<OperatorExecutor>>();
    this.streamManagerList = new ArrayList<StreamManager>();
  }

  public void addConnection(ComponentExecutor from, OperatorExecutor to) {
    if (!connectionMap.containsKey(from)) {
      connectionMap.put(from, new ArrayList<OperatorExecutor>());
    }
    connectionMap.get(from).add(to);
  }

  public void start() {
    // Set up executors for all the components.
    setupComponentExecutors();

    // All components are created now. Build the stream managers for the connections to
    // connect the component together.
    setupStreamManagers();

    // Start all stream managers first.
    startStreamManagers();

    // Start all component executors.
    startComponentExecutors();
  }

  private void setupComponentExecutors() {
    // Start from sources in the job.
    for (Source source: job.getSourceList()) {
      // For each source, traverse the the operations connected to it.
      List<OperatorExecutor> operatorExecutors = traverseComponent(source);

      // Start the current component.
      SourceExecutor executor = setupSourceExecutor(source);

      for (OperatorExecutor operatorExecutor : operatorExecutors) {
        addConnection(executor, operatorExecutor);
      }
    }
  }

  private void setupStreamManagers() {
    // All components are created now. Build the stream managers for the connections to
    // connect the component together.
    for (Map.Entry<ComponentExecutor, List<OperatorExecutor>> entry: connectionMap.entrySet()) {
      List<OperatorExecutor> targetQueues = new ArrayList<OperatorExecutor>();
      for (OperatorExecutor target: entry.getValue()) {
        targetQueues.add(target);
      }
      // 1 stream manager per "from" component
      StreamManager manager = new StreamManager(entry.getKey(), targetQueues);
      streamManagerList.add(manager);
    }
  }

  private SourceExecutor setupSourceExecutor(Source source) {
    SourceExecutor executor = new SourceExecutor(source);
    executorList.add(executor);

    return executor;
  }

  private OperatorExecutor setupOperationExecutor(Operator operation) {
    OperatorExecutor executor = new OperatorExecutor(operation);
    executorList.add(executor);

    return executor;
  }

  private List<OperatorExecutor> traverseComponent(IComponent component) {
    Stream stream = component.getOutgoingStream();

    List<Operator> operationList = stream.getOperationList();
    List<OperatorExecutor> operatorExecutors = new ArrayList<OperatorExecutor>();

    for (Operator op: operationList) {
      // Setup executors for the downstrea operators first.
      List<OperatorExecutor> downstreamExecutors = traverseComponent(op);
      // Start the current component.
      OperatorExecutor executor = setupOperationExecutor(op);

      for (OperatorExecutor downstreamExecutor : downstreamExecutors) {
        addConnection(executor, downstreamExecutor);
      }
      operatorExecutors.add(executor);
    }

    return operatorExecutors;
  }

  private void startComponentExecutors() {
    Collections.reverse(executorList);
    for (ComponentExecutor executor: executorList) {
      executor.start();
    }
  }

  private void startStreamManagers() {
    for (StreamManager streamManager: streamManagerList) {
      streamManager.start();
    }
  }
}
