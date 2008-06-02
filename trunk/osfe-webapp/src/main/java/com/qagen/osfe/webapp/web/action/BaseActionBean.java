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
package com.qagen.osfe.webapp.web.action;

import net.sourceforge.stripes.action.*;
import com.qagen.osfe.webapp.web.context.OsfeActionBeanContext;
import com.qagen.osfe.webapp.web.AbstractActionBeanContext;
import com.qagen.osfe.webapp.web.util.ViewConstants;

/**
 * Author: Gregg Bolinger
 * <p>
 */
public abstract class BaseActionBean implements ActionBean, ViewConstants {

 

  private AbstractActionBeanContext context;

  public void setContext(ActionBeanContext actionBeanContext) {
    this.context = (AbstractActionBeanContext)actionBeanContext;
  }

  public AbstractActionBeanContext getContext() {
    return this.context;
  }

  @DefaultHandler
  @DontValidate
  public abstract Resolution display();
}
