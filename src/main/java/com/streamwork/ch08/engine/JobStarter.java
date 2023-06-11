package com.streamwork.ch08.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.streamwork.ch08.api.Component;
import com.streamwork.ch08.api.Job;
import com.streamwork.ch08.api.Operator;
import com.streamwork.ch08.api.Source;
import com.streamwork.ch08.api.Stream;

public class JobStarter {
  private final static int QUEUE_SIZE = 64;

  // The job to start
  private final Job job;
  // List of executors and stream managers
  private final List<ComponentExecutor> executorList = new ArrayList<ComponentExecutor>();
  private final List<EventDispatcher> dispatcherList = new ArrayList<EventDispatcher>();

  // Connections between component executors
  private final List<Connection> connectionList = new ArrayList<Connection>();
  private final Map<Operator, OperatorExecutor> operatorMap = new HashMap<Operator, OperatorExecutor>();
  // A map from Operator executor to the incoming event queue of the event distributor in front of the operator.
  // Note that there is one event dispatcher and incoming queue for each incoming stream.
  private final Map<OperatorExecutor, Map<String, EventQueue>> operatorQueueMap = new HashMap<>();

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
    // All components are created now. Build the stream managers for the connections to
    // connect the component together.
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
    for (EventDispatcher dispatcher: dispatcherList) {
      dispatcher.start();
    }
  }

  private void connectExecutors(Connection connection) {
    // Each component executor could connect to multiple downstream operator executors.
    // For each of the downstream operator executor, there is a stream manager.
    // Each instance executor of the upstream executor connects to the all the stream managers
    // of the downstream executors first. And each stream manager connects to all the instance
    // executors of the downstream executor.
    connection.from.registerChannel(connection.channel);
    if (operatorQueueMap.containsKey(connection.to)) {
      // Existing operator. Connect to upstream only.
      Map<String, EventQueue> dispatcherQueues = operatorQueueMap.get(connection.to);
      EventQueue currentDispatcherQueue = dispatcherQueues.get(connection.streamName);
      if (currentDispatcherQueue != null) {
        connection.from.addOutgoingQueue(connection.channel, currentDispatcherQueue);
      } else {
        // The operator has upstream event dispatchers already, but this is a new incoming stream.
        EventDispatcher dispatcher = new EventDispatcher(connection.to);
        EventQueue dispatcherQueue = new EventQueue(QUEUE_SIZE, connection.streamName);
        dispatcherQueues.put(connection.streamName, dispatcherQueue);
        dispatcher.setIncomingQueue(dispatcherQueue);
        connection.from.addOutgoingQueue(connection.channel, dispatcherQueue);

        // Connect to downstream (to each instance).
        int parallelism = connection.to.getComponent().getParallelism();
        // The incoming queue for instance executor and the outgoing queue for event dispatcher
        // need to be named event queue so that the events contain stream name.
        //NamedEventQueue[] downstream = new NamedEventQueue[parallelism];
        //for (int i = 0; i < parallelism; ++i) {
        //  downstream[i] = new NamedEventQueue(QUEUE_SIZE, connection.streamName);
        //}
        //connection.to.setIncomingQueues(downstream);
        dispatcher.setOutgoingQueues(connection.to.getIncomingQueues());

        dispatcherList.add(dispatcher);
      }

    } else {
      // New operator. Create a dispatcher and connect to upstream first.
      EventDispatcher dispatcher = new EventDispatcher(connection.to);
      EventQueue dispatcherQueue = new EventQueue(QUEUE_SIZE, connection.streamName);
      dispatcher.setIncomingQueue(dispatcherQueue);
      Map<String, EventQueue> dispatcherQueues = new HashMap<>();
      dispatcherQueues.put(connection.streamName, dispatcherQueue);
      operatorQueueMap.put(connection.to, dispatcherQueues);
      connection.from.addOutgoingQueue(connection.channel, dispatcherQueue);

      // Connect to downstream (to each instance).
      int parallelism = connection.to.getComponent().getParallelism();
      // The incoming queue for instance executor and the outgoing queue for event dispatcher
      // need to be named event queue so that the events contain stream name.
      NamedEventQueue[] downstream = new NamedEventQueue[parallelism];
      for (int i = 0; i < parallelism; ++i) {
        downstream[i] = new NamedEventQueue(QUEUE_SIZE, connection.streamName);
      }
      connection.to.setIncomingQueues(downstream);
      dispatcher.setOutgoingQueues(downstream);

      dispatcherList.add(dispatcher);
    }
  }

  private void traverseComponent(Component component, ComponentExecutor executor) {
    Stream stream = component.getOutgoingStream();

    for (String channel: stream.getChannels()) {
      // Create one connection for each downstream operator.
      for (Map.Entry<Operator, String> operatorAndName:
          stream.getAppliedOperators(channel).entrySet()) {
        Operator operator = operatorAndName.getKey();
        String name = operatorAndName.getValue();
        OperatorExecutor operatorExecutor;
        if (!operatorMap.containsKey(operator)) {
          operatorExecutor = new OperatorExecutor(operator);
          operatorMap.put(operator, operatorExecutor);
          executorList.add(operatorExecutor);

          // Setup executors for the downstream operators.
          traverseComponent(operator, operatorExecutor);
        } else {
          operatorExecutor = operatorMap.get(operator);
        }
        connectionList.add(new Connection(executor, operatorExecutor, channel, name));
      }
    }
  }
}
