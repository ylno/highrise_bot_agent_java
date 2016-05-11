package de.benjaminborbe.bot.agent;

// type User struct {
// ID string `json:"id"`
// UserName string `json:"username"`
// DisplayName string `json:"displayname"`
// }

import java.io.Serializable;

public class RequestUser implements Serializable {

  private static final long serialVersionUID = 448457828783098074L;

  private String id;

  private String username;

  private String displayname;

  public String getDisplayname() {
    return displayname;
  }

  public void setDisplayname(final String displayname) {
    this.displayname = displayname;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }
}
