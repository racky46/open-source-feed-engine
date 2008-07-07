package com.qagen.osfe.webapp.service;

import com.qagen.osfe.webapp.model.MerchantRecommendation;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
public interface MerchantRecommendations {

  public MerchantRecommendation[] getRecomendations(String userId, String merchantName);

}