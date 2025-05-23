package org.example;

import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import io.micrometer.common.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

import static com.microsoft.azure.functions.annotation.AuthorizationLevel.ANONYMOUS;

@SpringBootApplication(exclude = GsonAutoConfiguration.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    ObservationRegistryCustomizer<?> customCountryTraceAttribute() {
        return registry -> registry.observationConfig()
            .observationFilter((context) -> {
                if (context instanceof ServerRequestObservationContext) {
                    context.addLowCardinalityKeyValue(KeyValue.of("country", "GB"));
                }
                return context;
            });
    }
}

@Component
class FunctionHandler {
    @Autowired
    RestClient.Builder builder;

    @FunctionName("HttpTriggerJava")
    public HttpResponseMessage run(
        @HttpTrigger(name = "req", methods = HttpMethod.GET, authLevel = ANONYMOUS)
        HttpRequestMessage<Optional<String>> request
    ) {
        RestClient restClient = builder.build();
        String responseBody = restClient.get()
            .uri("https://jsonplaceholder.typicode.com/todos/1")
            .retrieve()
            .body(String.class);
        return request.createResponseBuilder(HttpStatus.OK).body(responseBody).build();
    }
}