package com.qagen.osfe.dataAccess.vo;

import org.apache.commons.lang.builder.HashCodeBuilder;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

/**
 * model class generate from table t_feed_user
 *
 * @table t_feed_user
 */
public class FeedUser extends VO {
  private Integer feedUserId;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String emailAddres;
  private Timestamp dateCreated;
  private Timestamp dateModified;
  private Timestamp dateLastLogin;
  private Boolean locked;
  private FeedRole feedRole;


  public Integer getFeedUserId() {
    return feedUserId;
  }

  public void setFeedUserId(Integer feedUserId) {
    this.feedUserId = feedUserId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailAddres() {
    return emailAddres;
  }

  public void setEmailAddres(String emailAddres) {
    this.emailAddres = emailAddres;
  }

  public Timestamp getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Timestamp dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Timestamp getDateModified() {
    return dateModified;
  }

  public void setDateModified(Timestamp dateModified) {
    this.dateModified = dateModified;
  }

  public Timestamp getDateLastLogin() {
    return dateLastLogin;
  }

  public void setDateLastLogin(Timestamp dateLastLogin) {
    this.dateLastLogin = dateLastLogin;
  }

  public Boolean getLocked() {
    return locked;
  }

  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  public FeedRole getFeedRole() {
    return feedRole;
  }

  public void setFeedRole(FeedRole feedRole) {
    this.feedRole = feedRole;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof FeedUser)) {
      return false;
    }

    final FeedUser model = (FeedUser) object;
    if (feedUserId.equals(model.feedUserId)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder(17, 37);
    builder.append(feedUserId);
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final List<String> list = new ArrayList<String>();

    list.add(feedUserId.toString());

    return toString(list);
  }
}