package de.benjaminborbe.bot.agent;

import java.io.Serializable;

// type Response struct {
// Id ticket.Ticket `json:"ticket"`
// Message string `json:"message"`
// Replay bool `json:"replay"`
// }

public class Response implements Serializable {

  private static final long serialVersionUID = -868745826715838655L;

  private String ticket;

  private String message;

  private Boolean replay;

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public Boolean getReplay() {
    return replay;
  }

  public void setReplay(final Boolean replay) {
    this.replay = replay;
  }

  public String getTicket() {
    return ticket;
  }

  public void setTicket(final String ticket) {
    this.ticket = ticket;
  }
}
