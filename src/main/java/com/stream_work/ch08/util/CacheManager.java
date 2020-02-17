package com.stream_work.ch08.util;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.job.Logger;
import com.stream_work.ch08.job.TransactionEvent;

import java.io.Serializable;

public class CacheManager implements Serializable {

    private static final long serialVersionUID = -5235932753621047884L;
    private InMemoryCache cache;
    private Event event;

    public CacheManager(InMemoryCache cache) {
        this.cache = cache;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public synchronized void add() {
        TransactionEvent transactionEvent = (TransactionEvent) event;
        TransactionEvent cacheEvent = (TransactionEvent) this.cache.get(transactionEvent.getTransactionId());
        if (cacheEvent == null) {
            // transaction not found
            Logger.log("txn not found for : " + ((TransactionEvent) event.getData()).getTransactionId() + " -->\n");
            transactionEvent.addToFraudScore();
            Logger.log("cache size " + this.cache.size() + " -->\n");
            this.cache.add(transactionEvent.getTransactionId(), transactionEvent, 100000);
        } else {
            // transaction found
            Logger.log("TXN FOUND : " + cacheEvent.getAmount() + " -->\n");


        }
    }
}
