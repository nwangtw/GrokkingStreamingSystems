package com.stream_work.ch08.job;

import com.stream_work.ch08.api.EventCollector;
import com.stream_work.ch08.api.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

class TransactionSource extends Source {

  private static final long serialVersionUID = 2072016263985752626L;
  private int instance = 0;
  private final int portBase;

  private Socket socket;
  private BufferedReader reader;

  /**
   * Construct a bridge source.
   * @param name The name of the source.
   * @param parallelism
   * @param port The base port. Ports from number to base port + parallelism - 1
   *     are used by the instances of this component.
   */
  public TransactionSource(String name, int parallelism, int port) {
    super(name, parallelism);

    this.portBase = port;
  }

  /**
   * Initialize an instance. This function is called from the engine after the instance
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
      String amount = reader.readLine();
      if (amount == null) {
        // Exit when user closes the server.
        System.exit(0);
      }
      String transactionId = UUID.randomUUID().toString();

      eventCollector.add("default", new TransactionEvent(amount, transactionId));

      eventCollector.add("clone0", new TransactionEvent(amount, transactionId));

      eventCollector.add("clone1", new TransactionEvent(amount, transactionId));





      Logger.log("\n");  // A empty line before logging new events.
      Logger.log("transaction source (" + getName() + ") :: instance " + instance + " --> " + amount + "\n");
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
