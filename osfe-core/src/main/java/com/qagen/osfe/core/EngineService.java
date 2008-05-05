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
package com.qagen.osfe.core;

/**
 * Author: Hycel Taylor
 * <p/>
 * The EngineService abstract class defines the base contract for OSFE specific
 * engine services. A concrete engine service class performs common OSFE
 * specific operations and is used as a plug and play service in one or more
 * feed file configuration documents.
 */
public abstract class EngineService {
  protected EngineContext context;

  /**
   * Constructor
   *
   * @param context references the engine context.
   */
  protected EngineService(EngineContext context) {
    this.context = context;
  }

  /**
   * Stores the name of the given service as it is defined in the feed
   * configuration document.
   *
   * @return the name of the service as it is defined in the feed configuration
   *         document.
   */
  public abstract String name();

  /**
   * Use this method to reference engine context objects and other services
   * instead of the constructor.  Services may have inner dependencies with
   * each and are thus, initialized in two passes.  In the first pass, all
   * service constructors are instantiated.  In the second pass, the
   * initialization method on each service is called.
   */
  public abstract void initialize();

  /**
   * Depending on the behavior of the service, it's shutdown method may be
   * called in order to perform house keeping tasks such as closing files
   * and other depended services.
   */
  public abstract void shutdown();

}
