package de.benjaminborbe.bot.agent.runner;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

import de.benjaminborbe.bot.agent.Address;
import de.benjaminborbe.bot.agent.MessageHandler;
import de.benjaminborbe.bot.agent.Request;
import de.benjaminborbe.bot.agent.Response;
import de.benjaminborbe.bot.agent.Topic;

public class Runner {

  private static final Logger logger = LoggerFactory.getLogger(Runner.class);

  private final MessageHandler messageHandler;

  @Inject
  public Runner(final MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  public void run(final Address nsqdAddress, final Address nsqLookupdAddress, final String botname) {
    final NSQProducer producer = new NSQProducer().addAddress("localhost", 4150).start();

    final NSQLookup lookup = new DefaultNSQLookup();
    lookup.addLookupAddress(nsqLookupdAddress.getName(), nsqLookupdAddress.getPort());
    final NSQConsumer consumer = new NSQConsumer(lookup, Topic.REQUEST.getName(), botname, (message) -> {
      System.out.println("received: " + message);

      final Request request = jsonToRequest(message.getMessage());
      final Collection<Response> responses = messageHandler.HandleMessage(request);
      for (final Response resp : responses) {
        try {
          producer.produce(Topic.RESPONSE.getName(), responseToJson(resp));
        } catch (final Exception e) {
          logger.warn("send response failed", e);
        }
      }

      message.finished();
    });
    consumer.start();
  }

  public Request jsonToRequest(final byte[] json) {
    return new Request();
  }

  public byte[] responseToJson(final Response response) {
    return "foo".getBytes();
  }

}
