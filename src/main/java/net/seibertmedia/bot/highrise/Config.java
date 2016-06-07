package net.seibertmedia.bot.highrise;

import java.io.Serializable;

public class Config implements Serializable {

  private static final long serialVersionUID = -2440985182111371116L;

  private String authUser;

  private String authPassword;

  private String authAdress;

  private String restrictToTokens;

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

  public void setAuthAdress(final String authAdress) {
    this.authAdress = authAdress;
  }

  public String getAuthAdress() {
    return authAdress;
  }

  public void setRestrictToTokens(final String restrictToTokens) {
    this.restrictToTokens = restrictToTokens;
  }

  public String getRestrictToTokens() {
    return restrictToTokens;
  }
}
