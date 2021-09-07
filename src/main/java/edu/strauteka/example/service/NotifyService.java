package edu.strauteka.example.service;

import edu.strauteka.example.dto.Notify;
import edu.strauteka.example.dto.Ping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotifyService {
    private Long count = 0L;
    private final Sinks.Many<Notify> sink;
    private final Sinks.Many<ServerSentEvent<Notify>> serverSentEventSink;

    @Scheduled(fixedRate = 10000)
    public void publish() {
        this.publish(new Ping(count)).doFinally((sign) -> this.count++).subscribe();

    }

    public Flux<Sinks.EmitResult> publish(Notify notify) {
        final Sinks.EmitResult emitResult1 = this.sink.tryEmitNext(notify);
        final Sinks.EmitResult emitResult = this.serverSentEventSink.tryEmitNext(
                ServerSentEvent.builder(notify)
                        .comment("this is a comment and is ignored by the client")
                        .event(notify.getClass().getSimpleName())
                        .id(UUID.randomUUID().toString())
                        .retry(Duration.ofSeconds(3)).build());
        return Flux.just(emitResult1, emitResult);
    }
}
