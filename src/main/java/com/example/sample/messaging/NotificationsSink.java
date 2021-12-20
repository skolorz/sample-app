package com.example.sample.messaging;

import com.example.sample.messaging.payload.StompNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationsSink {

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final SimpMessagingTemplate stompTemplate;

	public NotificationsSink(SimpMessagingTemplate stompTemplate) {
		this.stompTemplate = stompTemplate;
	}

	@StreamListener("notifications")
	public void processStompNotification(StompNotification stompNotification)
			throws JsonProcessingException {
		stompTemplate.convertAndSend("/topic/" + stompNotification.join(),
				objectMapper.writeValueAsString(stompNotification));
	}
}
