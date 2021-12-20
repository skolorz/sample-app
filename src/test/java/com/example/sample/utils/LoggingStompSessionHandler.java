package com.example.sample.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

public class LoggingStompSessionHandler implements StompSessionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(LoggingStompSessionHandler.class);

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		LOG.info("Handling frame with payload " + payload);
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		LOG.info("Getting payload type");
		return Object.class;
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		LOG.error("Transport error occurred", exception);
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers,
			byte[] payload, Throwable exception) {

		LOG.error("Handling exception", exception);
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		LOG.info("Connected with session id " + session.getSessionId());
	}
}
