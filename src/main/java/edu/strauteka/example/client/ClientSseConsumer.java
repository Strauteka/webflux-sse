package edu.strauteka.example.client;

import edu.strauteka.example.dto.Notify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalTime;

@Slf4j
@Component
public class ClientSseConsumer {

    @Value("${server.port}")
    private Integer port;

    @Value("${server.address}")
    private String address;

    @Value("${server.ssl.enabled}")
    private Boolean sslEnabled;

    @Bean
    public CommandLineRunner consumeServerSentEvent() {
        return (args) -> {
            WebClient client = WebClient.create(baseUrl());
            ParameterizedTypeReference<ServerSentEvent<Notify>> type = new ParameterizedTypeReference<>() {};

            Flux<ServerSentEvent<Notify>> eventStream = client.get()
                    .uri("/v2")
                    .retrieve()
                    .bodyToFlux(type);

            eventStream.subscribe(
                    content -> log.info("Time: {} - event: {}, id:{}, content:{} ",
                            LocalTime.now(), content.event(), content.id(), content.data()),
                    error -> log.error("Error receiving SSE", error),
                    () -> log.info("Completed!!!"));
        };

    }

    private String baseUrl() {
        return String.format("http%s://%s:%d/sse",
                (sslEnabled ? "s" : ""),
                address,
                port);
    }

}
