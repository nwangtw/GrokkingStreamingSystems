package com.gss.ch02_with_stmgr.engine;

import com.gss.ch02.api.Event;
import com.gss.ch02.api.IComponent;
import com.gss.ch02.api.Job;
import com.gss.ch02.api.Operator;
import com.gss.ch02.api.Source;
import com.gss.ch02.api.Stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class JobStarter {
  private final static int QUEUE_SIZE = 64;

  // The job to start
  private final Job job;
  // List of executors and stream managers
  private final List<Process> processList = new ArrayList<Process>();
  
  private final Map<ComponentExecutor, List<OperatorExecutor>> connectionMap = new HashMap<ComponentExecutor, List<OperatorExecutor>>();
  // Each operator executor has a companion stream manager in front of it. This map is used to find the companion stream mamager from the operator executor.
  private final Map<OperatorExecutor, StreamManager> operatorStmgrMap = new HashMap<OperatorExecutor, StreamManager>();
  // When multiple executors connect to the same downstream operator executor, in fact, connect to its companion stream manager,
  // They need to share the same incoming event queue of the stream manager.
  private final Map<StreamManager, BlockingQueue<Event>> stmgrQueueMap = new HashMap<StreamManager, BlockingQueue<Event>>();

  public JobStarter(Job job) {
    this.job = job;
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

    // All components are created now. Build the connections to connect the components together.
    setupConnections();

    // Start all the processes.
    startProcesses();
  }

  private void setupComponentExecutors() {
    // Start from sources in the job and traverse components to create executors
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

  private void setupConnections() {
    // All components are created now. Build the stream managers for the connections to
    // connect the component together.
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
    Collections.reverse(processList);
    for (Process process: processList) {
      process.start();
    }
  }

  private void connectExecutors(ComponentExecutor from, OperatorExecutor to) {
    if (operatorStmgrMap.containsKey(to)) {
      // The operator executor has been connected before.
      StreamManager stmgr = operatorStmgrMap.get(to);
      from.setOutgoingQueue(stmgrQueueMap.get(stmgr));
    } else {
      // It is a new operator executor.
      StreamManager stmgr = new StreamManager();
      processList.add(stmgr);

      // Connect upstream executor with the stream manager
      BlockingQueue<Event> intermediateQueue = new ArrayBlockingQueue<Event>(QUEUE_SIZE);
      from.setOutgoingQueue(intermediateQueue);
      stmgr.setIncomingQueue(intermediateQueue);
      stmgrQueueMap.put(stmgr, intermediateQueue);
      // Connect stream manager with the downstream operator
      intermediateQueue = new ArrayBlockingQueue<Event>(QUEUE_SIZE);
      stmgr.setOutgoingQueue(intermediateQueue);
      to.setIncomingQueue(intermediateQueue);
    }
  }

  private SourceExecutor setupSourceExecutor(Source source) {
    SourceExecutor executor = new SourceExecutor(source);
    processList.add(executor);

    return executor;
  }

  private OperatorExecutor setupOperationExecutor(Operator operation) {
    OperatorExecutor executor = new OperatorExecutor(operation);
    processList.add(executor);

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
}
