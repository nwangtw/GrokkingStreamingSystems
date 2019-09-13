package com.gss.ch03.engine;

import com.gss.ch03.api.IComponent;
import com.gss.ch03.api.Job;
import com.gss.ch03.api.Operator;
import com.gss.ch03.api.Source;
import com.gss.ch03.api.Stream;

import java.util.ArrayList;
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
  private final List<ComponentExecutor> runnerList;
  private final Map<ComponentExecutor, List<OperatorExecutor>> connectionMap;
  private final List<StreamManager> streamManagerList;

  public JobRunner(Job job) {
    this.job = job;
    this.runnerList = new ArrayList<ComponentExecutor>();
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
    // Set up runners for all the components.
    setupComponentRunners();

    // All components are created now. Build the stream managers for the connections to
    // connect the component together.
    setupStreamManagers();

    // Start all stream managers first.
    startStreamManagers();

    // Start all component runners.
    startComponentRunners();
  }

  private void setupComponentRunners() {
    // Start from sources in the job.
    for (Source source: job.getSourceList()) {
      // For each source, traverse the the operations connected to it.
      List<OperatorExecutor> operatorRunners = traverseComponent(source);

      // Start the current component.
      SourceExecutor runner = setupSourceRunner(source);

      for (OperatorExecutor operatorRunner : operatorRunners) {
        addConnection(runner, operatorRunner);
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

  private SourceExecutor setupSourceRunner(Source source) {
    SourceExecutor runner = new SourceExecutor(source);
    runnerList.add(runner);

    return runner;
  }

  private OperatorExecutor setupOperationRunner(Operator operation) {
    OperatorExecutor runner = new OperatorExecutor(operation);
    runnerList.add(runner);

    return runner;
  }

  private List<OperatorExecutor> traverseComponent(IComponent component) {
    Stream stream = component.getOutgoingStream();

    List<Operator> operationList = stream.getOperationList();
    List<OperatorExecutor> operatorRunners = new ArrayList<OperatorExecutor>();

    for (Operator op: operationList) {
      // Setup runners for the downstrea operators first.
      List<OperatorExecutor> downstreamRunners = traverseComponent(op);
      // Start the current component.
      OperatorExecutor runner = setupOperationRunner(op);

      for (OperatorExecutor downstreamRunner : downstreamRunners) {
        addConnection(runner, downstreamRunner);
      }
      operatorRunners.add(runner);
    }

    return operatorRunners;
  }

  private void startComponentRunners() {
    for (ComponentExecutor runner: runnerList) {
      runner.start();
    }
  }

  private void startStreamManagers() {
    for (StreamManager streamManager: streamManagerList) {
      streamManager.start();
    }
  }
}
