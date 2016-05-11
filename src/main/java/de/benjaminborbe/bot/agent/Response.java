package de.benjaminborbe.bot.agent;

import java.io.Serializable;

public class Response implements Serializable {

  private static final long serialVersionUID = 8497755180200956949L;

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  private String message;
}
