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
package com.qagen.osfe.core.services;

import com.qagen.osfe.core.EngineService;
import com.qagen.osfe.core.ExceptionHandler;
import com.qagen.osfe.core.EngineContext;

/**
 * Author: Hycel Taylor
 * <p/>
 * This is just a place holder class as an example of a Concrete exception
 * handler.  It can be defined within the &lt;services&gt; element in a given
 * feed file configuration document.
 */
public class ExceptionHandlerPlaceHolder extends EngineService implements ExceptionHandler {

  /**
   * Constructor
   *
   * @param context reference to the engine context.
   */
  public ExceptionHandlerPlaceHolder(EngineContext context) {
    super(context);
  }

  /**
   * Set the reference to the exception and performs the necessary operations
   * to handle the given exception.
   *
   * @param exception the exception to handle.
   */
  public void handleException(Exception exception) {
    // Nothing to do here.
  }

  /**
   * Stores the name of the given service as it is defined in the feed
   * configuration document.
   *
   * @return the name of the service as it is defined in the feed configuration
   *         document.
   */
  public String name() {
    return this.getClass().getSimpleName();
  }

  /**
   * Use this method to reference engine context objects and other services
   * instead of the constructor.  Services may have inner dependencies with
   * each and are thus, initialized in two passes.  In the first pass, all
   * service constructors are instantiated.  In the second pass, the
   * initialization method on each service is called.
   */
  public void initialize() {
    // Nothing to do here.
  }

  /**
   * Depending on the behavior of the service, it's shutdown method may be
   * called in order to perform house keeping tasks such as closing files
   * and other depended services.
   */
  public void shutdown() {
    // Nothing to do here.
  }
}
