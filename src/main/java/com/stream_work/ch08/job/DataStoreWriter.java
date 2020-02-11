package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.api.EventCollector;
import com.stream_work.ch08.api.Operator;

public class DataStoreWriter extends Operator {
    private static final long serialVersionUID = -9132771852144360529L;

    public DataStoreWriter(String name, int parallelism) {
        super(name, parallelism);
    }

    @Override
    public void setupInstance(int instance) {

    }

    @Override
    public void apply(Event event, EventCollector eventCollector) {

    }
}
