package com.weison.sse.service;

import com.weison.sse.mapper.EventToSseEventMapper;
import com.weison.sse.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EventServiceImpl implements EventService {


    private List<SseEmitter> emitters;
    private List<Event> events;

    @Value("${sse-reconnect}")
    private Long reconnectInMilliseconds;

    @Autowired
    private EventToSseEventMapper eventToSseEventMapper;

    public EventServiceImpl() {
        this.events = new ArrayList<>();
        this.emitters = new ArrayList<>();
    }

    public List<SseEmitter> getEmitters() {
        return emitters;
    }

    public void setEmitters(List<SseEmitter> emitters) {
        this.emitters = emitters;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public void get(Event event) {
        this.events.add(event);
        log.info("Event is added.");
    }

    @Override
    public void send(Event event) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        log.info("Dead Emitter List is created.");

        this.emitters.forEach(emitter -> {
            try {
                SseEmitter.SseEventBuilder builder = eventToSseEventMapper
                        .map(event, reconnectInMilliseconds);
                log.info("Event is created.");

                emitter.send(builder);
                log.info("Event is sent to Emitter.");
            } catch (Exception exception) {
                deadEmitters.add(emitter);
                exception.printStackTrace();
                log.error("Emitter is added to Dead Emitter List: " + exception.getMessage());
            }
        });

        deadEmitters.forEach(emitter -> {
            this.emitters.remove(emitter);
            log.info("Dead Emitter is removed from Emitter List.");
        });
    }
}
