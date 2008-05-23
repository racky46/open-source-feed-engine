package com.qagen.osfe.dataAccess.vo;

import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;
import java.util.ArrayList;

/**
 * model class generate from table t_feed_role
 *
 * @table t_feed_role
 */
public class FeedRole extends VO {
  private Integer feedRoleId;
  private String roleName;
  private String roleDescription;


  public Integer getFeedRoleId() {
    return feedRoleId;
  }

  public void setFeedRoleId(Integer feedRoleId) {
    this.feedRoleId = feedRoleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleDescription() {
    return roleDescription;
  }

  public void setRoleDescription(String roleDescription) {
    this.roleDescription = roleDescription;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof FeedRole)) {
      return false;
    }

    final FeedRole model = (FeedRole) object;
    if (feedRoleId.equals(model.feedRoleId)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder(17, 37);
    builder.append(feedRoleId);
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final List<String> list = new ArrayList<String>();

    list.add(feedRoleId.toString());

    return toString(list);
  }
}