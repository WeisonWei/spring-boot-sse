package com.weison.sse.mapper;

import com.weison.sse.constant.DateTimeFormat;
import com.weison.sse.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class EventToSseEventMapper {


    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(DateTimeFormat.TIMESTAMP_FORMAT);

    public SseEventBuilder map(Event event, Long reconnectInMilliseconds) {
        SseEventBuilder sseEventBuilder = SseEmitter.event()
                .name(event.getName().toLowerCase())
                .data(event)
                .id(event.getTimestamp().format(TIMESTAMP_FORMATTER))
                .comment(event.getDescription())
                .reconnectTime(reconnectInMilliseconds);

        log.info("Event is mapped to SseEvent: " + event.getName().toLowerCase());

        return sseEventBuilder;
    }
}
