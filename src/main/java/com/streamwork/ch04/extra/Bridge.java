package com.streamwork.ch04.extra;

import com.streamwork.ch04.api.EventCollector;
import com.streamwork.ch04.api.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

class Bridge extends Source {
  private static final long serialVersionUID = 4914193100808497571L;

  private int instance = 0;
  private final int portBase;
  private boolean clone;

  private Socket socket;
  private BufferedReader reader;

  /**
   * Construct a bridge source.
   * @param name The name of the source.
   * @param parallelism
   * @param port The base port. Ports from number to base port + parallelism - 1
   *     are used by the instances of this component.
   * @param clone If this flag is true, events will be emitted into a "clone" channel
   *     in addition to the default channel.
   */
  public Bridge(String name, int parallelism, int port, boolean clone) {
    super(name, parallelism);

    this.portBase = port;
    this.clone = clone;
  }

  /**
   * Initialize an instance. This function is called from engine after the instance
   * is constructed.
   * @param instance The index of the instance.
   */
  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
    setupSocketReader(portBase + instance);
  }

  @Override
  public void getEvents(EventCollector eventCollector) {
    try {
      String vehicle = reader.readLine();
      if (vehicle == null) {
        // Exit when user closes the server.
        System.exit(0);
      }
      // This source emits events into two channels.
      eventCollector.add("default", new VehicleEvent(vehicle));
      if (clone) {
        eventCollector.add("clone", new VehicleEvent(vehicle + " clone"));
      }

      Logger.log("\n");  // A empty line before logging new events.
      Logger.log("bridge (" + getName() + ") :: instance " + instance + " --> " + vehicle + "\n");
    } catch (IOException e) {
      Logger.log("Failed to read input: " + e);
    }
  }

  /**
   * Set up a socket based reader object that reads strings from the port.
   * @param port
   */
  private void setupSocketReader(int port) {
    try {
      socket = new Socket("localhost", port);
      InputStream input = socket.getInputStream();
      reader = new BufferedReader(new InputStreamReader(input));
    } catch (UnknownHostException e) {
      e.printStackTrace();
      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
