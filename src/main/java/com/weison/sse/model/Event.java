package com.weison.sse.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weison.sse.constant.DateTimeFormat;
import com.weison.sse.constant.EventPriority;
import com.weison.sse.constant.EventStatus;
import com.weison.sse.constant.EventType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {

    private String name;

    private EventType type;

    private EventStatus status;

    private EventPriority priority;

    private String metadata;

    @JsonFormat(pattern = DateTimeFormat.TIMESTAMP_FORMAT)
    private LocalDateTime timestamp;

    private String description;

}
