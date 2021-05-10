package com.streamwork.ch07.api;

/**
 * This is a time based fixed windowing strategy. It is a special case of sliding time window.
 */
public class FixedTimeWindowingStrategy extends SlidingTimeWindowingStrategy {
    public FixedTimeWindowingStrategy(long intervalMillis, long watermarkMillis) {
        super(intervalMillis, intervalMillis, watermarkMillis);
    }
}
