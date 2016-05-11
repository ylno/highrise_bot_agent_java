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

  public static Address fromEnv(final String env) {
    final String address = System.getenv(env);
    if (address == null)
      throw new IllegalArgumentException(String.format("env %s missing", env));
    try {
      final String[] parts = address.split(":", 2);
      return new Address(parts[0], Integer.parseInt(parts[1]));
    } catch (final Exception e) {
      throw new IllegalArgumentException(String.format("parse %s=%s failed", env, address));
    }
  }
}
