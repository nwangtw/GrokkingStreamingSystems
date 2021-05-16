package com.streamwork.ch08.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a time based fixed windowing strategy. It supports
 */
public class SlidingTimeWindowingStrategy implements WindowingStrategy {
    // Length of each window in milli seconds.
    private final long lengthMillis;
    // Interval between two adjacent windows. For fixed windows, interval should be equivalent to length.
    // For sliding windows, interval is shorter than length. The start time of each window should be
    // interval x N.
    private final long intervalMillis;
    // Extra wait time before window closing. A window should be closed when the current *processing time*
    // is greater than the window end time (event time based) plus an extra watermark time.
    private final long watermarkMillis;

    // Keep track of multiple event windows. Because of sliding and watermark, there could be multiple
    // event windows existing in a strategy.
    private final Map<Long, EventWindow> eventWindows = new HashMap<>();

    public SlidingTimeWindowingStrategy(long lengthMillis, long intervalMillis, long watermarkMillis) {
        this.lengthMillis = lengthMillis;
        this.intervalMillis = intervalMillis;
        this.watermarkMillis = watermarkMillis;
    }

    boolean isLateEvent(long eventTime, long processingTime) {
        return eventTime < processingTime - lengthMillis - watermarkMillis;
    }

    /**
     * Add an event into the windowing strategy. Note that all calculation in this function are event time
     * based.
     * @param event
     */
    @Override
    public void add(Event event, long processingTime) {
        // Check event type.
        if (!(event instanceof TimedEvent)) {
          throw new RuntimeException("Timed events are required by time based WindowingStrategy");
        }
        TimedEvent timedEvent = (TimedEvent) event;
        long eventTime = timedEvent.getTime();
        // Skip late events.
        if (!isLateEvent(eventTime, processingTime)) {
            // Add event to all the windows cover its time.
            long mostRecentStartTime = eventTime / intervalMillis * intervalMillis;
            long startTime = mostRecentStartTime;
            // Iterate backwards until the window close time is earlier than the event time.
            while (eventTime < startTime + lengthMillis) {
                EventWindow window = eventWindows.get(startTime);
                if (window == null) {
                    window = new EventWindow(startTime, startTime + lengthMillis);
                    eventWindows.put(startTime, window);
                }
                window.add(event);
                // Move to the previous window.
                startTime -= intervalMillis;
            }
        }
    }

    /**
     * Get the event windows that are ready to be processed. It is based on the current processing time.
     * @return the list of event windows to be processed.
     */
    @Override
    public List<EventWindow> getEventWindows(long processingTime) {
        // Return that windows that are ready to be processed. Typically there should be zero or one window.
        List<EventWindow> toProcess = new ArrayList<>();
        for (Map.Entry<Long, EventWindow> entry: eventWindows.entrySet()) {
            long startTime = entry.getKey();
            if (processingTime >= startTime + lengthMillis + watermarkMillis) {
                toProcess.add(entry.getValue());
            }
        }
        // Clean up.
        for (EventWindow window: toProcess) {
            eventWindows.remove(window.getStartTime());
        }
        return toProcess;
    }
}
