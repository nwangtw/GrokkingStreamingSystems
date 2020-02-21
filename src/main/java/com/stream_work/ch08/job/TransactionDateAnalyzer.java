package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.api.EventCollector;
import com.stream_work.ch08.api.Operator;

import java.time.LocalDate;

public class TransactionDateAnalyzer extends Operator {
    private static final long serialVersionUID = 6282478250144019523L;
    private int instance = 0;

    public TransactionDateAnalyzer(String name, int parallelism) {
        super(name, parallelism);
    }


    @Override
    public void setupInstance(int instance) {
        this.instance = instance;
    }

    @Override
    public void apply(Event event, EventCollector eventCollector) {
        Logger.log("txn count analyzer (" + getName() + ") :: instance " + instance + " -->\n" + event.getData() + "\n");
        TransactionEvent transactionEvent = (TransactionEvent) event;
        if (transactionEvent.getTransactionDate().isEqual(LocalDate.now())) {
            Logger.log("The transation is good!!!!\n");
        } else {
            // This
            Logger.log("it's bad!!!\n");
            ((TransactionEvent)event).addToFraudScore();
        }

        eventCollector.add("default", event);
    }
}
