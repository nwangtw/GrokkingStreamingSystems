package com.streamwork.ch02.job;

import java.net.*;
import java.io.*;
import java.util.List;

import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.api.Source;

class SensorReader extends Source {
  private final BufferedReader reader;

  public SensorReader(String name, int port) {
    super(name);

    reader = setupSocketReader(port);
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
      System.out.println("");  // An empty line before logging new events
      System.out.println("SensorReader --> " + vehicle);
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
