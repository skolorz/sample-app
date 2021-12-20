package com.example.sample.messaging.payload;

public class StompNotification {

	private String a;

	private String contextId;

	private String contextFilter;

	private Object messagePayload;

	public final String getA() {
		return a;
	}

	public final void setA(String a) {
		this.a = a;
	}

	public final String getContextId() {
		return contextId;
	}

	public final void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public final String getContextFilter() {
		return contextFilter;
	}

	public final void setContextFilter(String contextFilter) {
		this.contextFilter = contextFilter;
	}


	public final void setMessagePayload(Object messagePayload) {
		this.messagePayload = messagePayload;
	}

	public String join() {
		return a + '/' + contextId + '/' + contextFilter;
	}
}
