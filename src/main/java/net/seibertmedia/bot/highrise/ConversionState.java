package net.seibertmedia.bot.highrise;

public interface ConversionState {

  String getQuestion();

  String getFailMessage();

  void execute(String answer, Credentials credentials);

  boolean isValid(String answer);

  boolean isFinal();
}
