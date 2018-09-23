package com.uzys.chat.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ServerEndpoint(value = "/websocket")
public class WebSocketEndpoint {

    private static final Logger log = LogManager.getLogger(WebSocketEndpoint.class);

    @OnOpen
    public void onOpen(Session session) {
        log.info("New session opened");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info(message);
    }

    @OnClose
    public void onClose(Session session) {
        log.info("Close session");
    }

    @OnError
    public void onError(Session session, Throwable t) {
        log.warn("Error in socket connection {}", t);
    }

}
