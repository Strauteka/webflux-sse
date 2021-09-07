package edu.strauteka.example.configuration;

import edu.strauteka.example.dto.Notify;
import edu.strauteka.example.dto.Ping2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Configuration
public class NotifyConfiguration {

    @Bean
    public Sinks.Many<Notify> sink(){
        return Sinks.many().replay().latest();
    }

    @Bean
    public Flux<Notify> flux(Sinks.Many<Notify> sink) {
        Flux<Notify> ping = Flux.interval(Duration.ofMillis(5000)).map(Ping2::new);
        return Flux.merge( sink.asFlux(), ping);
    }

    @Bean
    public Sinks.Many<ServerSentEvent<Notify>> serverSentEventSink(){
        return Sinks.many().replay().latest();
    }

    @Bean
    public Flux<ServerSentEvent<Notify>> serverSentEventFlux(Sinks.Many<ServerSentEvent<Notify>> sink) {
        return sink.asFlux();
    }
}
