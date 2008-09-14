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
public abstract class EngineService implements EngineServiceable {
  protected EngineContext context;

  public void setContext(EngineContext context) {
    this.context = context;
  }

}
