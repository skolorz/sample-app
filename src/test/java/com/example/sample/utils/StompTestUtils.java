package com.example.sample.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.stream.converter.ObjectStringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

public class StompTestUtils {

	public static void withStompSession(String url, Consumer<StompSession> consumer) {
		WebSocketClient webSocketClient = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
		stompClient.setMessageConverter(new ObjectStringMessageConverter());
		StompSessionHandler stompSessionHandler = new LoggingStompSessionHandler() {

			@Override
			public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				super.afterConnected(session, connectedHeaders);
				consumer.accept(session);
			}
		};

		WebSocketHttpHeaders socketHttpHeaders = new WebSocketHttpHeaders();
		StompHeaders stompHeaders = new StompHeaders();
		socketHttpHeaders.add("Authorization", buildJwt());
		stompClient.connect(url, socketHttpHeaders, stompHeaders, stompSessionHandler);
	}

	private static String buildJwt() {
		JwtBuilder jwtBuilder = Jwts
				.builder()
				.setId(UUID.randomUUID().toString())
				.setIssuer("Some tests")
				.setIssuedAt(Date.from(Instant.now()))
				.claim("userName", "TestUser");

		return "Bearer " + jwtBuilder.compact();
	}
}
