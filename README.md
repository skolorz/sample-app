This project requires docker to run test.

The test passes or fails with
```
~[spring-cloud-sleuth-instrumentation-3.0.3.jar:3.0.3]
at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128) ~[na:na]
at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628) ~[na:na]
at java.base/java.lang.Thread.run(Thread.java:829) ~[na:na]
Caused by: java.lang.IllegalStateException: Already immutable
at org.springframework.util.Assert.state(Assert.java:76) ~[spring-core-5.3.8.jar:5.3.8]
at org.springframework.messaging.support.NativeMessageHeaderAccessor.removeNativeHeader(NativeMessageHeaderAccessor.java:277) ~[spring-messaging-5.3.8.jar:5.3.8]
at org.springframework.cloud.sleuth.instrument.messaging.MessageHeaderPropagatorSetter.removeAnyTraceHeaders(MessageHeaderPropagatorSetter.java:62) ~[spring-cloud-sleuth-instrumentation-3.0.3.jar:3.0.3]
at org.springframework.cloud.sleuth.instrument.messaging.TracingChannelInterceptor.beforeHandle(TracingChannelInterceptor.java:276) ~[spring-cloud-sleuth-instrumentation-3.0.3.jar:3.0.3]
at org.springframework.messaging.support.ExecutorSubscribableChannel$SendTask.applyBeforeHandle(ExecutorSubscribableChannel.java:167) ~[spring-messaging-5.3.8.jar:5.3.8]
at org.springframework.messaging.support.ExecutorSubscribableChannel$SendTask.run(ExecutorSubscribableChannel.java:140) ~[spring-messaging-5.3.8.jar:5.3.8]
... 4 common frames omitted
```
