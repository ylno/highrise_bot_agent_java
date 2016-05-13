package de.benjaminborbe.bot.highrise;

public class Config {

  private String authUser;
  private String authPassword;

  public void setAuthPassword(final String authPassword) {
    this.authPassword = authPassword;
  }

  public String getAuthUser() {
    return authUser;
  }

  public void setAuthUser(final String authUser) {
    this.authUser = authUser;
  }

  public String getAuthPassword() {
    return authPassword;
  }

}
