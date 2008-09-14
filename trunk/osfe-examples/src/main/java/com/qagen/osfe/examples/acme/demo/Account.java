/* Copyright 2008 Hycel Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qagen.osfe.examples.acme.demo;

import java.sql.Timestamp;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class Account {
  private final Integer memberId;
  private final Integer transactionId;
  private final Double transactionAmount;
  private final Timestamp transactionDate;

  public Account(Integer memberId, Integer transactionId, Double transactionAmount, Timestamp transactionDate) {
    this.memberId = memberId;
    this.transactionId = transactionId;
    this.transactionAmount = transactionAmount;
    this.transactionDate = transactionDate;
  }

  public Integer getMemberId() {
    return memberId;
  }

  public Integer getTransactionId() {
    return transactionId;
  }

  public Double getTransactionAmount() {
    return transactionAmount;
  }

  public Timestamp getTransactionDate() {
    return transactionDate;
  }

  public String toString() {
    final String BAR = "|";
    final StringBuilder builder = new StringBuilder();

    builder.append(memberId)
      .append(BAR)
      .append(transactionId)
      .append(BAR)
      .append(transactionAmount)
      .append(BAR)
      .append(transactionDate);

    return builder.toString();
  }
}
