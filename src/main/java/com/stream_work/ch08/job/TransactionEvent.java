package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.util.MockDataUtils;

import java.util.Date;
import java.util.UUID;

public class TransactionEvent extends Event {
  private String merchandiseId;
  private String transactionId;
  private Double amount;
  private Date transactionTime;
  private String userAccount;
  private Double latitude;
  private Double longitude;

  TransactionEvent(String amount) {
    this.amount = Double.valueOf(amount);
    this.merchandiseId = UUID.randomUUID().toString();
    this.transactionId = UUID.randomUUID().toString();
    this.transactionTime = new Date();
    this.userAccount = MockDataUtils.getUserAccount();
    MockDataUtils.LatLongHolder holder = MockDataUtils.getTransactionLocation();
    this.latitude = holder.getLatitude();
    this.longitude = holder.getLongitude();


  }

  @Override
  public Double getData() {
    return amount;
  }

  private void setUserAccount() {

  }
}
