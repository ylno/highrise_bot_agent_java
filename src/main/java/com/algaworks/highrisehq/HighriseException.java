package com.algaworks.highrisehq;

/**
 * 
 * @author thiagofa
 *
 */
public class HighriseException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private int statusCode;

  public HighriseException(final int statusCode, final String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public HighriseException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(final int statusCode) {
    this.statusCode = statusCode;
  }

}
