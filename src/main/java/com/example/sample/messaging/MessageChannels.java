package com.example.sample.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageChannels {

	@Input("notifications")
	SubscribableChannel stompNotifications();
}
