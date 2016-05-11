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

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request implements Serializable {

  private static final long serialVersionUID = 8497755180200956949L;

  private String ticket;

  @JsonProperty("private")
  private Boolean priv;

  private String authToken;

  private String message;

  private RequestUser from;

  private String bot;

  public String getAuthToken() {
    return authToken;
  }

  public String getBot() {
    return bot;
  }

  public RequestUser getFrom() {
    return from;
  }

  public Boolean getPriv() {
    return priv;
  }

  public void setAuthToken(final String authToken) {
    this.authToken = authToken;
  }

  public void setBot(final String bot) {
    this.bot = bot;
  }

  public void setFrom(final RequestUser from) {
    this.from = from;
  }

  public void setPriv(final Boolean priv) {
    this.priv = priv;
  }

  public void setTicket(final String ticket) {
    this.ticket = ticket;
  }

  public String getTicket() {
    return ticket;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }
}
