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

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.integration.spring.SpringBean;
import com.qagen.osfe.webapp.model.User;
import com.qagen.osfe.webapp.web.action.auth.HomeActionBean;
import com.qagen.osfe.dataAccess.vo.FeedUser;
import com.qagen.osfe.dataAccess.service.FeedUserService;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
@UrlBinding("/action/login")
public class LoginActionBean extends BaseActionBean {

  @ValidateNestedProperties({
  @Validate(field = "username", required = true),
  @Validate(field = "password", required = true)
      })
  private FeedUser user;

  private FeedUserService feedUserService;

  public FeedUser getUser() {
    return user;
  }

  public void setUser(FeedUser user) {
    this.user = user;
  }

  @SpringBean("feedUserService")
  public void setFeedUserService(FeedUserService feedUserService) {
    this.feedUserService = feedUserService;
  }

  @Override
  public Resolution display() {
    return new ForwardResolution(LOGIN_VIEW);
  }

  public Resolution login() {
    FeedUser fu = feedUserService.authenticateFeedUser(user);
    if (fu == null) {
      getContext().getValidationErrors().add("login", new LocalizableError("/action/login.invalid"));
      return getContext().getSourcePageResolution();
    }else{
      getContext().setFeedUser(fu);
      return new RedirectResolution(HomeActionBean.class);
    }
  }
}
