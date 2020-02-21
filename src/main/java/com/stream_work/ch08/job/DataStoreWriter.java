package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.api.EventCollector;
import com.stream_work.ch08.api.Operator;
import com.stream_work.ch08.util.CacheManager;

public class DataStoreWriter extends Operator {
    private static final long serialVersionUID = -9132771852144360529L;

    private CacheManager cacheManager;
    private int instance = 0;

    public DataStoreWriter(String name, int parallelism, CacheManager cacheManager) {
        super(name, parallelism);
        this.cacheManager = cacheManager;
    }

    @Override
    public void setupInstance(int instance) {
        this.instance = instance;

    }

    @Override

    public void apply(Event event, EventCollector eventCollector) {

        TransactionEvent transactionEvent = (TransactionEvent) event.getData();
        Logger.log(transactionEvent.toString());
        Logger.log("dataStoreWriter (" + getName() + ") :: instance " + instance + " -->\n" + transactionEvent.getTransactionId()+ " -->\n");
        this.cacheManager.add(event);


    }


}
