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
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Author: Hycel Taylor
 * <p/>
 * This class generates n number of Account objects with
 * fairly radomly generated data.
 */
public class AccountGenerator {
  private Integer numberOfAccounts;
  private Integer counter;

  private Integer startNumber = 100000;
  private Random generator = new Random();

  public AccountGenerator(Integer numberOfAccounts) {
    this.numberOfAccounts = numberOfAccounts;
    this.counter = 0;
  }

  public boolean hasNext() {
    return (counter <= numberOfAccounts);
  }

  public Account next() {
    if (hasNext()) {
      counter++;

      final Integer memberId = startNumber + generator.nextInt(100000);
      final Integer transactionId = counter + startNumber;
      final Double amount = generator.nextDouble() * generator.nextInt(10000);
      final Integer day = generator.nextInt(31);
      final GregorianCalendar gc = new GregorianCalendar(2008, 8, day);
      final Timestamp timestamp = new Timestamp(gc.getTimeInMillis());

      return new Account(memberId, transactionId, amount, timestamp);
    }

    return null;
  }
}
