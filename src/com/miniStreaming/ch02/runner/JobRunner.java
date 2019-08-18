package com.miniStreaming.ch02.runner;

import com.miniStreaming.ch02.job.IComponent;
import com.miniStreaming.ch02.job.Job;
import com.miniStreaming.ch02.job.Operator;
import com.miniStreaming.ch02.job.Source;
import com.miniStreaming.ch02.job.Stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class JobRunner {
  private class Connection {
    private final IComponentRunner from;
    private final IComponentRunner to;

    public Connection(IComponentRunner from, IComponentRunner to) {
      this.from = from;
      this.to = to;
    }

    public IComponentRunner getFrom() { return from; }
    public IComponentRunner getTo() { return to; }
  }

  private final Job job;
  private final List<IComponentRunner> runnerList;
  private final Map<IComponentRunner, List<OperatorRunner>> connectionMap;
  private final List<StreamManager> streamManagerList;

  public JobRunner(Job job) {
    this.job = job;
    this.runnerList = new ArrayList<IComponentRunner>();
    this.connectionMap = new HashMap<IComponentRunner, List<OperatorRunner>>();
    this.streamManagerList = new ArrayList<StreamManager>();
  }

  public void addConnection(IComponentRunner from, OperatorRunner to) {
    if (!connectionMap.containsKey(from)) {
      connectionMap.put(from, new ArrayList<OperatorRunner>());
    }
    connectionMap.get(from).add(to);
  }

  public void start() {
    SetupRunners();

    startRunners();
  }

  private void SetupRunners() {
    // Start from sources in the job.
    for (Map.Entry<String, Source> entry: job.getSourceMap().entrySet()) {
      // For each source, traverse the the operations connected to it.
      List<OperatorRunner> operatorRunners = traverseComponent(entry.getValue());

      // Start the current component.
      SourceRunner runner = setupSourceRunner(entry.getValue());

      for (OperatorRunner operatorRunner : operatorRunners) {
        addConnection(runner, operatorRunner);
      }
    }

    // All the components are started, Build the stream managers for the connections.
    for (Map.Entry<IComponentRunner, List<OperatorRunner>> entry: connectionMap.entrySet()) {
      List<BlockingQueue> toStreams = new ArrayList<BlockingQueue>();
      for (OperatorRunner to: entry.getValue()) {
        toStreams.add(to.getIncomingStream());
      }
      // 1 stream manager per "from" component
      StreamManager manager = new StreamManager(entry.getKey().getOutgoingStream(), toStreams);
      streamManagerList.add(manager);
    }
  }

  private void startRunners() {
    // Start all stream managers first.
    startStreamManagers();

    // Start all components.
    startComponentRunners();
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
      // Setup downstream component first.
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
    for (IComponentRunner runner: runnerList) {
      runner.start();
    }
  }

  private void startStreamManagers() {
    for (StreamManager streamManager: streamManagerList) {
      streamManager.start();
    }
  }
}
