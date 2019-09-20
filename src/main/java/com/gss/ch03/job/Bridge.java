package com.gss.ch03.job;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.Source;

import java.net.*;
import java.io.*;
import java.util.List;

class Bridge extends Source {
  private final int portBase;
  private int instance = 0;
  private BufferedReader reader = null;

  public Bridge(String name, int parallelism, int port) {
    super(name, parallelism);

    this.portBase = port;
  }

  @Override
  public void setupInstance(int instance) {
    instance = instance;
    reader = setupSocketReader(portBase + instance);
  }

  @Override
  public void getEvents(List<Event> eventCollector) {
    try {
      String vehicle = reader.readLine();
      if (vehicle == null) {
        // Exit when user closes the server.
        System.exit(0);
      }
      eventCollector.add(new VehicleEvent(vehicle));
      System.out.println("bridge :: instance " + instance + " --> " + vehicle);
    } catch (IOException e) {
      System.out.println("Failed to read input: " + e);
    }
  }

  private BufferedReader setupSocketReader(int port) {
    try {
      Socket socket = new Socket("localhost", port);
      InputStream input = socket.getInputStream();
      return new BufferedReader(new InputStreamReader(input));
    } catch (UnknownHostException e) {
      e.printStackTrace();
      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
    return null;
  }
}
