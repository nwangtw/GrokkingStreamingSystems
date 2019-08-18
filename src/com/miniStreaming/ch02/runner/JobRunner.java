package com.miniStreaming.ch02.runner;

import com.miniStreaming.ch02.job.Component;
import com.miniStreaming.ch02.job.Job;
import com.miniStreaming.ch02.job.Operation;
import com.miniStreaming.ch02.job.Source;
import com.miniStreaming.ch02.job.Stream;

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
  private final Map<ComponentRunner, List<OperationRunner>> connectionMap;
  private final List<StreamManager> streamManagerList;

  public JobRunner(Job job) {
    this.job = job;
    this.runnerList = new ArrayList<ComponentRunner>();
    this.connectionMap = new HashMap<ComponentRunner, List<OperationRunner>>();
    this.streamManagerList = new ArrayList<StreamManager>();
  }

  public void addConnection(ComponentRunner from, OperationRunner to) {
    if (!connectionMap.containsKey(from)) {
      connectionMap.put(from, new ArrayList<OperationRunner>());
    }
    connectionMap.get(from).add(to);
  }

  public void start() {
    for (Map.Entry<String, Source> entry: job.getSourceMap().entrySet()) {
      // For each source, traverse the the operations connected to it.
      List<OperationRunner> operationRunners = traverseOperations(entry.getValue());

      // Start the current component.
      SourceRunner runner = startSource(entry.getValue());

      for (OperationRunner operationRunner : operationRunners) {
        addConnection(runner, operationRunner);
      }
    }

    // All the components are started, Build the stream managers for the connections
    for (Map.Entry<ComponentRunner, List<OperationRunner>> entry: connectionMap.entrySet()) {
      List<BlockingQueue> toStreams = new ArrayList<BlockingQueue>();
      for (OperationRunner to: entry.getValue()) {
        toStreams.add(to.getIncomingStream());
      }
      // 1 stream manager per "from" component
      StreamManager manager = new StreamManager(entry.getKey().getOutgoingStream(), toStreams);
      manager.start();
    }
  }

  private <T> SourceRunner<T> startSource(Source<T> source) {
    SourceRunner<T> runner = new SourceRunner<T>(source);
    runnerList.add(runner);

    runner.start();

    return runner;
  }

  private <I, T> OperationRunner<I, T> startOperation(Operation<I, T> operation) {
    OperationRunner<I, T> runner = new OperationRunner<I, T>(operation);
    runnerList.add(runner);

    runner.start();

    return runner;
  }

  private List<OperationRunner> traverseOperations(Component component) {
    Stream stream = component.getOutgoingStream();

    Map<String, Operation> operationMap = stream.getOperationMap();
    List<OperationRunner> operationRunners = new ArrayList<OperationRunner>();

    for (Map.Entry<String, Operation> entry: operationMap.entrySet()) {
      Operation op = entry.getValue();
      // Setup downstream component first.
      List<OperationRunner> downstreamRunners = traverseOperations(op);
      // Start the current component.
      OperationRunner runner = startOperation(entry.getValue());

      for (OperationRunner downstreamRunner : downstreamRunners) {
        addConnection(runner, downstreamRunner);
      }
      operationRunners.add(runner);
    }

    return operationRunners;
  }
}
