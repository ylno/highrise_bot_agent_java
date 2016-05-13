package de.benjaminborbe.bot.highrise;

public class ConversionStateSubdomain implements ConversionState {

  String question = "I need to know your subdomain at first. If you use yourname.highrisehq.com please only give me the first part (e.g. yourname). What is your subdomain?";

  String failMessage = "Thats no valid subdomain. Please try again. If you use yourname.highrisehq.com please only give me the first part (e.g. yourname). What is your subdomain?";

  @Override
  public String getQuestion() {
    return question;
  }

  @Override
  public String getFailMessage() {
    return failMessage;
  }

  @Override
  public void execute(String answer, Credentials credentials) {
    credentials.setUserName(answer);
  }

  @Override
  public boolean isValid(String answer) {
    if (!answer.isEmpty())
      return true;
    return false;
  }

  @Override
  public boolean isFinal() {
    return false;
  }
}
