package com.stream_work.ch02.job;

import java.net.*;
import java.io.*;
import java.util.List;

import com.stream_work.ch02.api.Event;
import com.stream_work.ch02.api.Source;

class Bridge extends Source {
  private Socket socket;
  private BufferedReader reader;

  /**
   * Construct a bridge source.
   * @param name The name of the source.
   * @param port The port used by this component.
   */
  public Bridge(String name, int port) {
    super(name);

    setupSocketReader(port);
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

      System.out.println("");  // A empty line before logging new events.
      System.out.println("bridge --> " + vehicle);
    } catch (IOException e) {
      System.out.println("Failed to read input: " + e);
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
