package de.benjaminborbe.bot.agent;

//
// type Request struct {
// Id ticket.Ticket `json:"ticket"`
// Private bool `json:"private"`
// AuthToken string `json:"authToken"`
// Message string `json:"message"`
// From *User `json:"from"`
// Bot string `json:"bot"`
// }
//
// type User struct {
// ID string `json:"id"`
// UserName string `json:"username"`
// DisplayName string `json:"displayname"`
// }

import java.io.Serializable;

public class Request implements Serializable {

  private static final long serialVersionUID = -5921065954053277024L;

  private String message;

  private String bot;

  public String getBot() {
    return bot;
  }

  public void setBot(final String bot) {
    this.bot = bot;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }
}
