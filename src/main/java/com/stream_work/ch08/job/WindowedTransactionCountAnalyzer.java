package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.api.EventCollector;
import com.stream_work.ch08.api.Operator;

import java.util.HashMap;
import java.util.Map;


public class WindowedTransactionCountAnalyzer extends Operator {
    private static final long serialVersionUID = 6282478250144019523L;
    private Map<String, Integer> countMap = new HashMap<String, Integer>();
    private int instance = 0;

    public WindowedTransactionCountAnalyzer(String name, int parallelism) {
        super(name, parallelism);
    }


    @Override
    public void setupInstance(int instance) {
        this.instance = instance;
    }

    @Override
    public void apply(Event event, EventCollector eventCollector) {
        Logger.log("apply (" + getName() + ") :: instance " + instance + " -->\n" + event.getData() + "\n");
    }
}
