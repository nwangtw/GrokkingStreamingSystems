package com.gss.ch02.runner;

import com.gss.ch02.api.IComponent;
import com.gss.ch02.api.Job;
import com.gss.ch02.api.Operator;
import com.gss.ch02.api.Source;
import com.gss.ch02.api.Stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class JobRunner {
  private class Connection {
    private final ComponentRunner from;
    private final ComponentRunner to;

    public Connection(ComponentRunner from, ComponentRunner to) {
      this.from = from;
      this.to = to;
    }

    public ComponentRunner getFrom() { return from; }
    public ComponentRunner getTo() { return to; }
  }

  private final Job job;
  private final List<ComponentRunner> runnerList;
  private final Map<ComponentRunner, List<OperatorRunner>> connectionMap;
  private final List<StreamManager> streamManagerList;

  public JobRunner(Job job) {
    this.job = job;
    this.runnerList = new ArrayList<ComponentRunner>();
    this.connectionMap = new HashMap<ComponentRunner, List<OperatorRunner>>();
    this.streamManagerList = new ArrayList<StreamManager>();
  }

  public void addConnection(ComponentRunner from, OperatorRunner to) {
    if (!connectionMap.containsKey(from)) {
      connectionMap.put(from, new ArrayList<OperatorRunner>());
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
    for (Map.Entry<String, Source> entry : job.getSourceMap().entrySet()) {
      // For each source, traverse the the operations connected to it.
      List<OperatorRunner> operatorRunners = traverseComponent(entry.getValue());

      // Start the current component.
      SourceRunner runner = setupSourceRunner(entry.getValue());

      for (OperatorRunner operatorRunner : operatorRunners) {
        addConnection(runner, operatorRunner);
      }
    }
  }

  private void setupStreamManagers() {
    // All components are created now. Build the stream managers for the connections to
    // connect the component together.
    for (Map.Entry<ComponentRunner, List<OperatorRunner>> entry: connectionMap.entrySet()) {
      List<BlockingQueue> targetQueues = new ArrayList<BlockingQueue>();
      for (OperatorRunner target: entry.getValue()) {
        targetQueues.add(target.getIncomingQueue());
      }
      // 1 stream manager per "from" component
      StreamManager manager = new StreamManager(entry.getKey().getOutgoingQueue(), targetQueues);
      streamManagerList.add(manager);
    }
  }

  private void startRunners() {

  }

  private <T> SourceRunner<T> setupSourceRunner(Source<T> source) {
    SourceRunner<T> runner = new SourceRunner<T>(source);
    runnerList.add(runner);

    return runner;
  }

  private <I, T> OperatorRunner<I, T> setupOperationRunner(Operator<I, T> operation) {
    OperatorRunner<I, T> runner = new OperatorRunner<I, T>(operation);
    runnerList.add(runner);

    return runner;
  }

  private List<OperatorRunner> traverseComponent(IComponent component) {
    Stream stream = component.getOutgoingStream();

    Map<String, Operator> operationMap = stream.getOperationMap();
    List<OperatorRunner> operatorRunners = new ArrayList<OperatorRunner>();

    for (Map.Entry<String, Operator> entry: operationMap.entrySet()) {
      Operator op = entry.getValue();
      // Setup runners for the downstrea operators first.
      List<OperatorRunner> downstreamRunners = traverseComponent(op);
      // Start the current component.
      OperatorRunner runner = setupOperationRunner(entry.getValue());

      for (OperatorRunner downstreamRunner : downstreamRunners) {
        addConnection(runner, downstreamRunner);
      }
      operatorRunners.add(runner);
    }

    return operatorRunners;
  }

  private void startComponentRunners() {
    for (ComponentRunner runner: runnerList) {
      runner.start();
    }
  }

  private void startStreamManagers() {
    for (StreamManager streamManager: streamManagerList) {
      streamManager.start();
    }
  }
}
