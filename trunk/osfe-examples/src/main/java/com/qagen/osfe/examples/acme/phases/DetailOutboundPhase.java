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
package com.qagen.osfe.examples.acme.phases;

import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.phases.outbound.delimited.DetailPhase;
import com.qagen.osfe.examples.acme.demo.AccountGenerator;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class DetailOutboundPhase extends DetailPhase {
  private Integer numberOfAccounts;
  private AccountGenerator generator;

  
  /**
   * Defines the number of accounts to generate.
   *
   * <ul><li>Injection - required</li></ul>
   *
   * @param numberOfAccounts number of accounts to generate.
   */
  public void setNumberOfAccounts(Integer numberOfAccounts) {
    this.numberOfAccounts = numberOfAccounts;
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    super.initialize();
    generator = new AccountGenerator(numberOfAccounts);
  }

  public void execute() {
    Integer counter = 0;
    while (generator.hasNext()) {
      writeBean(generator.next());

      if (counter % 10000 == 0) {
        System.out.println("Currently generated " + counter + " rows...");
      }
      counter++;
    }
  }

  public void shutdown() {
  }
}
