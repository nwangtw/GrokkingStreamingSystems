package com.streamwork.ch08.job;

import java.io.Serializable;
import java.util.Map;

/**
 * ScoreStorage is a class to simulate a very basic key-value store using a hashmap object.
 */
class EmissionTable implements Serializable {
  private String KEY_DELIMITER = ":";
  private final Map<String, Double> data;

  public EmissionTable() {
    data = Map.of(
      "XXX:AA:2020:90", 3.3,
      "YYY:CC:2021:90", 4.2
    );
  }

  /**
   * Get the score of a transaction.
   * @param transaction
   * @param default
   * @return
   */
  double getEmission(String make, String model, int year, float temperature, double defaultValue) {
    String key = make + KEY_DELIMITER + model + KEY_DELIMITER + year + KEY_DELIMITER + Math.round(temperature);
    Double value = data.get(key);
    if (value == null) {
        return defaultValue;
    }
    return value;
  }
}
