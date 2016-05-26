package de.benjaminborbe.bot.highrise;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 4980310389006572462L;

  public UserNotFoundException(final String authToken) {
    super(String.format("no user with token {} found", authToken));
  }
}
