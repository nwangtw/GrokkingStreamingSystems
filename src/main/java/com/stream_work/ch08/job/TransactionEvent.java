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
  private Integer fraudScore = 0;

  TransactionEvent(String amount, String transactionId) {
    this.amount = Double.valueOf(amount);
    this.merchandiseId = UUID.randomUUID().toString();
    this.transactionId = transactionId;
    this.transactionTime = new Date();
    this.userAccount = MockDataUtils.getUserAccount();
    MockDataUtils.LatLongHolder holder = MockDataUtils.getTransactionLocation();
    this.latitude = holder.getLatitude();
    this.longitude = holder.getLongitude();

  }

  public void addToFraudScore() {
    this.fraudScore++;
  }

  public Integer getFraudScore() {
    return fraudScore;
  }

  public void accumulateFraudScore(Integer componentFraudScore) {
    Logger.log("accumulating " + this.fraudScore + " and " + componentFraudScore + "\n");
    this.fraudScore += componentFraudScore;
  }

  @Override
  public TransactionEvent getData() {
    return this;
  }

  public String getMerchandiseId() {
    return merchandiseId;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public Double getAmount() {
    return amount;
  }

  public Date getTransactionTime() {
    return transactionTime;
  }

  public String getUserAccount() {
    return userAccount;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  @Override
  public String toString() {
    return "TransactionEvent{" +
            "merchandiseId='" + merchandiseId + '\'' +
            ", transactionId='" + transactionId + '\'' +
            ", amount=" + amount +
            ", transactionTime=" + transactionTime +
            ", userAccount='" + userAccount + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", fraudScore=" + fraudScore +
            '}';
  }
}
