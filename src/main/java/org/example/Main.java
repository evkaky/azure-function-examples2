package org.example;

import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Optional;

public class Main {
    private final Logger log = LoggerFactory.getLogger(Main.class);

    @FunctionName("HttpTriggerJava")
    public HttpResponseMessage run(
        @HttpTrigger(name = "req", methods = HttpMethod.GET, authLevel = AuthorizationLevel.ANONYMOUS)
        HttpRequestMessage<Optional<String>> request
    ) {
        // key=val pair is going to appear in "customDimensions" field in "traces" table in application insights
        // for entry with message=test1
        MDC.put("key", "val");
        log.info("test1");
        MDC.remove("key");

        // but here
        // key=val pair is NOT going to appear in "customDimensions" field in "traces" table in application insights
        // for entry with message=test2
        // I think this is wrong. Azure functions runtime probably should instrument addKeyValue() method as well
        log.atInfo()
            .addKeyValue("key", "val")
            .log("test2");

        return request.createResponseBuilder(HttpStatus.OK).body("response").build();
    }

    @FunctionName("MyServiceBusTrigger")
    @FixedDelayRetry(maxRetryCount = 2, delayInterval = "00:00:10")
    public void serviceBusTrigger(
        @ServiceBusQueueTrigger(name = "message", queueName = "test-queue", connection = "AzureServiceBusConnection")
        ServiceBusReceivedMessage message,
        final ExecutionContext context
    ) {
        System.exit(1);
        context.getLogger().info("Message Id: " + message.getMessageId());
    }
}
