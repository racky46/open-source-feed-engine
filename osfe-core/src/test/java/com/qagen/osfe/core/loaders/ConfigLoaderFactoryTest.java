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

import com.qagen.osfe.core.Loader;

import java.util.Map;

public class ConfigLoaderFactoryTest {

  public static void main(String[] args) {
    final String configFile = "/partnerConfig/qagen/feed/test/config.xml";
    final ConfigLoaderFactory factory = new ConfigLoaderFactory(configFile);

    final Map<String, Loader> map = factory.getLoaderMap();

    System.out.println(map);
  }
}
