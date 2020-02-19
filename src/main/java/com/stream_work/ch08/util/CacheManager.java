package com.stream_work.ch08.util;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.job.Logger;
import com.stream_work.ch08.job.TransactionEvent;

import java.io.Serializable;

public class CacheManager implements Serializable {

    private static final long serialVersionUID = -5235932753621047884L;
    private InMemoryCache cache;

    public CacheManager(InMemoryCache cache) {
        this.cache = cache;
    }

    public synchronized void add(Event event) {
        TransactionEvent transactionEvent = (TransactionEvent) event;
        TransactionEvent cacheEvent = (TransactionEvent) this.cache.get(transactionEvent.getTransactionId());
        if (cacheEvent == null) {
            // transaction not found
            Logger.log("txn not found for : " + transactionEvent.getTransactionId() + " -->\n");
            Logger.log("fraud score: " + transactionEvent.getFraudScore()+ "\n");
            //transactionEvent.accumulateFraudScore(transactionEvent.getFraudScore());
            this.cache.add(transactionEvent.getTransactionId(), transactionEvent, 100000);
        } else {
            // transaction found
            Logger.log("txn found for : " + transactionEvent.getTransactionId() + " -->\n");
            Logger.log("fraud score: " + transactionEvent.getFraudScore()+ "\n");
            cacheEvent.accumulateFraudScore(transactionEvent.getFraudScore());
            this.cache.add(cacheEvent.getTransactionId(),cacheEvent, 100000 );
            Logger.log("Total fraud score : " + cacheEvent.getFraudScore() + " -->\n");
        }
        Logger.log("cache size " + this.cache.size() + " -->\n");

    }
}
