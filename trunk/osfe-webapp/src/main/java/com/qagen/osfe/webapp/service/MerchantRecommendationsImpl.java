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

import javax.jws.WebMethod;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
public class MerchantRecommendationsImpl implements MerchantRecommendations {

  @WebMethod
  public MerchantRecommendation[] getRecommendations(String userId, String merchantName) {
    final MerchantRecommendation[] merchants = new MerchantRecommendation[3];

    merchants[0] = new MerchantRecommendation("EXPDIIA001", "expedia.com");
    merchants[1] = new MerchantRecommendation("WALMRT001", "Wal-Mart");
    merchants[2] = new MerchantRecommendation("123NJK001", "123Inkjets.com");

    return merchants;
  }

}
