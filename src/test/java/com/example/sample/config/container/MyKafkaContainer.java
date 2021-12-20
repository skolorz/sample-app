package com.example.sample.config.container;

import io.vavr.Lazy;
import org.testcontainers.containers.wait.strategy.Wait;

public class MyKafkaContainer {

	public static final Lazy<org.testcontainers.containers.KafkaContainer> KAFKA_CONTAINER = Lazy.of(() -> {
		org.testcontainers.containers.KafkaContainer container =
				new org.testcontainers.containers.KafkaContainer("5.5.2");
		container.setWaitStrategy(Wait.forListeningPort());
		container.start();
		return container;
	});

}
