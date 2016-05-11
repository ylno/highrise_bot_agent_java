package de.benjaminborbe.bot.agent;

public class Address {

  private final String name;

  private final int port;

  public String getName() {
    return name;
  }

  public int getPort() {
    return port;
  }

  public Address(final String name, final int port) {
    this.name = name;
    this.port = port;
  }

  public static Address parse(final String address) {
    if (address == null)
      throw new IllegalArgumentException("parameter address missing");
    final String[] parts = address.split(":", 2);
    return new Address(parts[0], Integer.parseInt(parts[1]));
  }
}
