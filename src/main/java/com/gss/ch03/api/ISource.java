package com.gss.ch03.api;

/**
 * The interface for the sources.
 * A source accepts events from the outside world and emits into the system
 * via the outgoing stream,
 */
public interface ISource extends IComponent {
  /**
   * Set up this source object.
   */
  void setup();
}
