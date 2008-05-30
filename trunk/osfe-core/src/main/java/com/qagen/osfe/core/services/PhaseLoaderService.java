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

import com.qagen.osfe.core.*;
import com.qagen.osfe.core.utils.BeanPopulator;
import com.qagen.osfe.core.vo.PhaseInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * This class is the standard PhaseLoadeService the comes with OSFE for
 * loading and instantiating phases defined within a given feed configuration
 * file.
 */
public class PhaseLoaderService extends EngineService implements PhaseHandler {

  /**
   * Constructor
   *
   * @param context references the engine context.
   */
  public PhaseLoaderService(EngineContext context) {
    super(context);
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
  }

  /**
   * Set the reference to a list of PhaseInfo objects.
   *
   * @param phaseInfoList refence to a list of PhaseInfo objects.
   * @return list of concrete Phase objects.
   */
  public List<Phase> loadPhases(List<PhaseInfo> phaseInfoList) {
    try {
      final List<Phase> phaseList = new ArrayList<Phase>();

      for (PhaseInfo phaseInfo : phaseInfoList) {
        if (phaseInfo.getEnable()) {
          final Class clazz = Class.forName(phaseInfo.getClassName());
          final Class argTypes[] = new Class[]{EngineContext.class, String.class};
          final Constructor constructor = clazz.getConstructor(argTypes);
          final String name = phaseInfo.getName();
          final Phase phase = (Phase) constructor.newInstance(context, name);

          // Check to see if any properties of the phase need to be set.
          BeanPopulator.populatePhaseProperties(phase, phaseInfo.getPropertyMap());

          phaseList.add(phase);
        }
      }

      return phaseList;

    } catch (ClassNotFoundException e) {
      throw new FeedErrorException(e);
    } catch (NoSuchMethodException e) {
      throw new FeedErrorException(e);
    } catch (InstantiationException e) {
      throw new FeedErrorException(e);
    } catch (IllegalAccessException e) {
      throw new FeedErrorException(e);
    } catch (InvocationTargetException e) {
      throw new FeedErrorException(e);
    }
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
