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
package com.qagen.osfe.core.loaders;

import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.core.FeedDocumentReader;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.Loader;
import com.qagen.osfe.core.vo.Clazz;
import org.dom4j.Document;
import org.dom4j.Element;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigLoaderFactory {
  private Map<String, Loader> loaderMap;
  private Document document;

  private static Log logger = Log.getInstance(ConfigLoaderFactory.class);

  public ConfigLoaderFactory(String configFile) {
    try {
      document = FeedDocumentReader.parseDocument(configFile);
      loadConfigLoaders(document.getRootElement());
    } catch (Exception e) {
      throw new FeedErrorException(e);
    }
  }

  private void log(int size, String name) {
    logger.info("ConfigLoaderFactory loading " + name + ". Imported " + size + " elements.");
  }

  private void loadConfigLoaders(Element root) {
    final ConfigLoader configLoader = new ConfigLoader(root);
    final List<Clazz> list = configLoader.getList();

    loaderMap = new HashMap<String, Loader>();

    try {
      for (Clazz clazz : list) {
        final String name = clazz.getName();
        final Class configclass = Class.forName(clazz.getClassName());
        final Class argTypes[] = new Class[]{Element.class};
        final Constructor constructor = configclass.getConstructor(argTypes);
        final Loader loader = (Loader) constructor.newInstance(root);

        loaderMap.put(name, loader);
        log(loader.size(), name);
      }

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

  public Map<String, Loader> getLoaderMap() {
    return loaderMap;
  }

  public Document getDocument() {
    return document;
  }
}
