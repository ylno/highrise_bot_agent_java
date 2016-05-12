package de.benjaminborbe.bot.hello;

import de.benjaminborbe.bot.highrise.Credentials;

public interface ConversionState {

  String getQuestion();

  String getFailMessage();

  void execute(String answer, Credentials credentials);

  boolean isValid(String answer);

  boolean isFinal();
}
