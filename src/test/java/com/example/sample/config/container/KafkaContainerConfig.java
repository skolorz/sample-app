package com.example.sample.config.container;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;
import java.util.Properties;

import static com.example.sample.config.container.MyKafkaContainer.KAFKA_CONTAINER;

@Configuration
public class KafkaContainerConfig {

	@PostConstruct
	private void setup() {
		System.out.println("Init kafka container on address "
				+ KAFKA_CONTAINER.get().getHost() + ":" + KAFKA_CONTAINER.get().getFirstMappedPort()
		);
		System.setProperty("spring.kafka.bootstrap-servers",
				KAFKA_CONTAINER.get().getHost() + ":" + KAFKA_CONTAINER.get().getFirstMappedPort());
	}

	@Bean
	@Lazy
	public KafkaProducer<String, String> kafkaTestProducer() {
		var producerProperties = new Properties();
		producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.get().getBootstrapServers());
		producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new KafkaProducer<>(producerProperties);
	}
}
