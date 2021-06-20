package com.streamwork.ch08.job;

import java.io.*;
import java.net.*;

import com.streamwork.ch08.api.EventCollector;
import com.streamwork.ch08.api.Source;

class VehicleEventSource extends Source {
  private int instance = 0;
  private final int portBase;

  private Socket socket;
  private BufferedReader reader;

  /**
   * Construct a transaction source to receive transactions.
   * @param name The name of the source.
   * @param parallelism
   * @param port The base port. Ports from number to base port + parallelism - 1
   *     are used by the instances of this component.
   */
  public VehicleEventSource(String name, int parallelism, int port) {
    super(name, parallelism);

    this.portBase = port;
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
      String transaction = reader.readLine();
      if (transaction == null) {
        // Exit when user closes the server.
        System.exit(0);
      }

      // The input is {vehicle_make},{vehicle_model},{vehicle_year},{zoneId}. For example, xxx,aa,2020,3.
      try {
        String[] values = transaction.split(",");
        String make = values[0];
        String model = values[1];
        int year = Integer.parseInt(values[2]);
        int zoneId = Integer.parseInt(values[3]);

        VehicleEvent event = new VehicleEvent(make, model, year, zoneId, System.currentTimeMillis());
        eventCollector.add(event);

        Logger.log("\n");  // A empty line before logging new events.
        Logger.log("vehicle (" + getName() + ") :: instance " + instance + " --> " + event + "\n");
      } catch (Exception e) {
        Logger.log("Vehicle input needs to be in this format: {make},{model},{year},{zoneId}. For example: xxx,aa,2020,3\n");
        return; // No transaction to emit.
      }
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
