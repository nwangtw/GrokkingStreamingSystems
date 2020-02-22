package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.util.MockDataUtils;

import java.time.LocalDate;
import java.util.UUID;

public class TransactionEvent extends Event {
  private String merchandiseId;
  private String transactionId;
  private Double amount;
  private LocalDate transactionDate;
  private String userAccount;
  private Integer fraudScore = 0;
  private LatLongHolder userLocation;
  private LatLongHolder transactionLocation;

  TransactionEvent(String amount, String transactionId) {
    this.amount = Double.valueOf(amount);
    this.merchandiseId = UUID.randomUUID().toString();
    this.transactionId = transactionId;
    this.transactionDate = MockDataUtils.getRandomTransactionLocalDate();
    this.userAccount = MockDataUtils.getRandomUserAccount();
    this.userLocation = MockDataUtils.getRandomLocation();
    this.transactionLocation = MockDataUtils.getRandomLocation();
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

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public String getUserAccount() {
    return userAccount;
  }

  public LatLongHolder getUserLocation() {
    return userLocation;
  }

  public LatLongHolder getTransactionLocation() {
    return transactionLocation;
  }

  @Override
  public String toString() {
    return "TransactionEvent{" +
            "merchandiseId='" + merchandiseId + '\'' +
            ", transactionId='" + transactionId + '\'' +
            ", amount=" + amount +
            ", transactionDate=" + transactionDate +
            ", userAccount='" + userAccount + '\'' +
            ", fraudScore=" + fraudScore +
            ", userLocation=" + userLocation +
            ", transactionLocation=" + transactionLocation +
            '}';
  }
}
