package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.api.EventCollector;
import com.stream_work.ch08.api.Operator;

public class DataStoreWriter extends Operator {
    private static final long serialVersionUID = -9132771852144360529L;
    private int instance = 0;

    public DataStoreWriter(String name, int parallelism) {
        super(name, parallelism);
    }

    @Override
    public void setupInstance(int instance) {
        Logger.log("dataStore writer setup");

    }

    @Override
    public void apply(Event event, EventCollector eventCollector) {
        Logger.log("dataStoreWriter (" + getName() + ") :: instance " + instance + " -->\n" + event.getData());

    }
}
