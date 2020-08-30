package com.weison.sse.service;


import com.weison.sse.model.Event;

public interface EventService {

    void get(Event event);

    void send(Event event);
}
