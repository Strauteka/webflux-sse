package edu.strauteka.example.controller;

import edu.strauteka.example.dto.Notify;
import edu.strauteka.example.dto.NotifyData;
import edu.strauteka.example.service.NotifyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("sse")
public class SseController {

    private final Flux<Notify> notifyFlux;
    private final NotifyService notifyService;

    private final Flux<ServerSentEvent<Notify>> notifyServerSentEvent;

    @GetMapping(value = "v1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
     Flux<Notify> getNotify() {
        return this.notifyFlux.doOnCancel(() -> log.info("Sse Canceled"));
    }

    @GetMapping(value = "v2",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<ServerSentEvent<Notify>> notifyServerSentEvent() {
        return this.notifyServerSentEvent.doOnCancel(() -> log.info("Sse Canceled"));
    }

    @GetMapping(value = "v1/{event}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Notify> getNotify(@PathVariable("event") String event) {
        return this.notifyFlux.filter(notify -> notify.getKey().name().equals(event));
    }

    @GetMapping(value = "v2/{event}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<ServerSentEvent<Notify>> notifyServerSentEvent(@PathVariable("event") String event) {
        return this.notifyServerSentEvent
                .filter(ev -> Objects.equals(ev.event(), event));
    }

    @GetMapping("publish/{data}")
    Flux<Sinks.EmitResult> publish(@PathVariable("data") String data) {
        return this.notifyService.publish(new NotifyData(data));
    }

}
