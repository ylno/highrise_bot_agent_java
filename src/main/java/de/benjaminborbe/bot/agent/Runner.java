package de.benjaminborbe.bot.agent;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

public class Runner {

  private static final Logger logger = LoggerFactory.getLogger(Runner.class);

  private final MessageHandler messageHandler;

  private final JsonToRequestMapper jsonToRequestMapper;

  private final ResponseToJsonMapper responseToJsonMapper;

  @Inject
  public Runner(final MessageHandler messageHandler, final JsonToRequestMapper jsonToRequestMapper,
      final ResponseToJsonMapper responseToJsonMapper) {
    this.messageHandler = messageHandler;
    this.jsonToRequestMapper = jsonToRequestMapper;
    this.responseToJsonMapper = responseToJsonMapper;
  }

  public void run(final Address nsqdAddress, final Address nsqLookupdAddress, final String botname) {
    final NSQProducer producer = new NSQProducer().addAddress("localhost", 4150).start();
    final NSQLookup lookup = new DefaultNSQLookup();
    lookup.addLookupAddress(nsqLookupdAddress.getName(), nsqLookupdAddress.getPort());
    final NSQConsumer consumer = new NSQConsumer(lookup, Topic.REQUEST.getName(), botname, (message) -> {
      logger.debug("process message: {}", message);
      try {
        final Request request = jsonToRequestMapper.map(message.getMessage());
        final Collection<Response> responses = messageHandler.HandleMessage(request);
        logger.debug("go {} responses", responses.size());
        for (final Response response : responses) {
          try {
            response.setTicket(request.getTicket());
            producer.produce(Topic.RESPONSE.getName(), responseToJsonMapper.map(response));
          } catch (final Exception e) {
            logger.warn("send response failed", e);
          }
        }
      } catch (final IOException e) {
        logger.warn("fromEnv request json failed", e);
      }
      logger.debug("process message finsihed");
      message.finished();
    });
    consumer.start();
  }
}
