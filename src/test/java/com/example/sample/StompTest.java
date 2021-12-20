package com.example.sample;

import com.example.sample.messaging.payload.StompNotification;
import com.example.sample.utils.StompTestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.stream.test.binder.TestSupportBinderAutoConfiguration;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;
import static org.awaitility.Awaitility.await;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = TestSupportBinderAutoConfiguration.class)
@AutoConfigureWireMock(port = 12345)
class StompTest {

    private static final long MESSAGE_SEND_DELAY = 1L;

	@LocalServerPort
	private String serverPort;

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Autowired
	private ObjectMapper objectMapper;

	@RepeatedTest(4)
	void thisTestSometimesFails() {
        var payloadRef = new AtomicReference<String>();

		StompTestUtils.withStompSession("ws://localhost:" + serverPort + "/sample/stomp", session -> {
			StompSession.Subscription subscribe = session.subscribe("/topic/yyyy/42/xxxx",
					new TestStompFrameHandler(payloadRef));
			sendToKafka();
        });

		await()
//				.pollDelay(Duration.ofSeconds(1))
				.until(() -> nonNull(payloadRef.get()));

	}


	private String buildFolderStompNotification(Object msgPayload) {
		try {
			StompNotification stompNotification = new StompNotification();
			stompNotification.setA("yyyy");
			stompNotification.setContextId("42");
			stompNotification.setContextFilter("xxxx");
			stompNotification.setMessagePayload(msgPayload);

			return objectMapper.writeValueAsString(stompNotification);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}


    private void sendToKafka() {
        await().pollDelay(Duration.ofSeconds(MESSAGE_SEND_DELAY)).until(() -> true);
        kafkaProducer.send(new ProducerRecord<>("notifications", buildFolderStompNotification("hello")));
    }
    private static final class TestStompFrameHandler implements StompFrameHandler {

        private final AtomicReference<String> payloadRef;
		private boolean hasPayload = false;

		private TestStompFrameHandler(AtomicReference<String> payloadRef) {
            this.payloadRef = payloadRef;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return String.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
			hasPayload = true;
            payloadRef.set((String) payload);
        }

		public boolean isHasPayload() {
			return hasPayload;
		}
	}
}
