package net.seibertmedia.bot.highrise;

import java.io.Serializable;

public class Credentials implements Serializable {

  private static final long serialVersionUID = 7171308877273415744L;

  private String userName;

  private String apiKey;

  public String getUserName() {
    return userName;
  }

  public void setUserName(final String userName) {
    this.userName = userName;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(final String apiKey) {
    this.apiKey = apiKey;
  }
}
