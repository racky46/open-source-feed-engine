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
package com.qagen.osfe.webapp.service;

import com.qagen.osfe.webapp.model.MerchantRecommendation;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
public class MerchantRecommendationsImpl implements MerchantRecommendations {
  public MerchantRecommendation[] getRecomendations(String userId, String merchantName) {
    final MerchantRecommendation[] merchants = new MerchantRecommendation[3];

    merchants[0] = new MerchantRecommendation("4", "1-800-Flowers.com");
    merchants[1] = new MerchantRecommendation("518", "39DollarGlasses.com");
    merchants[3] = new MerchantRecommendation("561", "Acorn Online");

    return merchants;
  }

}
